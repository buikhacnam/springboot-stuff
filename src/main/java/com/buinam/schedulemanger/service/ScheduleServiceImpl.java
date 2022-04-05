package com.buinam.schedulemanger.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.buinam.schedulemanger.dto.LazyLoadDTO;
import com.buinam.schedulemanger.dto.ScheduleDTO;
import com.buinam.schedulemanger.dto.ScheduleDetailDTO;
import com.buinam.schedulemanger.mapper.GenerateMapper;
import com.buinam.schedulemanger.model.MapSchedule;
import com.buinam.schedulemanger.model.Schedule;
import com.buinam.schedulemanger.model.ScheduleCategories;
import com.buinam.schedulemanger.repository.MapScheduleRepository;
import com.buinam.schedulemanger.repository.ScheduleCategoriesRepository;
import com.buinam.schedulemanger.repository.ScheduleRepository;
import com.buinam.schedulemanger.utils.CommonUtils;
import com.buinam.schedulemanger.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.google.common.base.Strings;


@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleCategoriesRepository scheduleCategoriesRepository;

    @Autowired
    private MapScheduleRepository mapScheduleRepository;

    @Autowired
    private GenerateMapper mapper;

    @Autowired
    private EntityManager em;

    @Override
    public LazyLoadDTO searchSchedule(String pageSize, String pageNumber, String name, String strFromDate, String strToDate, String description, String location, String textSearch, String orderBy, String orderType) {

        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;
        String strFormat = "yyyy-MM-dd"; 

        System.out.println("strFromDate: " + strFromDate); // 2022-03-07

        if (!Strings.isNullOrEmpty(strFromDate)) {
            fromDate = DateUtil.convertStringToLocalDateTime(strFromDate, strFormat);
        }
        if (!Strings.isNullOrEmpty(strToDate)) {
            toDate = DateUtil.convertStringToLocalDateTime(strToDate, strFormat);
        }
        if (fromDate != null && (toDate == null || fromDate.isEqual(toDate))) {
            toDate = fromDate.plusDays(1L);
        }

        System.out.println("fromDate: " + fromDate); // 2022-03-07T00:00

        // first get the count of result the query will get
        LazyLoadDTO lazyLoadDTO = executeSearchScheduleByCondition(pageSize, pageNumber, name, fromDate, toDate, description, location, textSearch, orderBy, orderType, true);


        // if the result is not empty (count > 0 or whatever), then use the count from the last query and  execute the query to get the schedule list
        if(lazyLoadDTO != null) {

            System.out.println("MAKE IT HERE!!!!!!!!!!!!!!!!!");
           BigDecimal count = lazyLoadDTO.getCount();
            
            // if count is greater than 0
            if(count.compareTo(BigDecimal.ZERO) > 0) {
                // get the schedule list
                lazyLoadDTO = executeSearchScheduleByCondition(pageSize, pageNumber, name, fromDate, toDate, description, location, textSearch, orderBy, orderType, false);
                lazyLoadDTO.setCount(count);
            }
        }

        
        return lazyLoadDTO;

    }

    private LazyLoadDTO executeSearchScheduleByCondition(String pageSize, String pageNumber, String name, LocalDateTime fromDate, LocalDateTime toDate, String description, String location, String textSearch, String orderBy, String orderType, boolean isCount) {
        
        LazyLoadDTO lazyLoadDTO = new LazyLoadDTO();
        List<Object> listParam = new ArrayList<>();
        StringBuilder strQuery = new StringBuilder();


        if(isCount) {
            strQuery.append("SELECT COUNT(*) FROM schedule s WHERE 1=1 ");
        } else {
            strQuery.append("SELECT * FROM schedule s WHERE 1=1 ");
        } 

        if(!Strings.isNullOrEmpty(name)) {
            strQuery.append(" AND name LIKE ? ");
            listParam.add("%" + name + "%");
        }

        if(fromDate != null) {
            strQuery.append(" AND create_date_time >= ? ");
            listParam.add(fromDate);
        }

        if(toDate != null) {
            strQuery.append(" AND create_date_time <= ? ");
            listParam.add(toDate);
        }

        if(!Strings.isNullOrEmpty(description)) {
            strQuery.append(" AND description LIKE ? ");
            listParam.add("%" + description + "%");
        }

        if(!Strings.isNullOrEmpty(location)) {
            strQuery.append(" AND location LIKE ? ");
            listParam.add("%" + location + "%");
        }

        if (!Strings.isNullOrEmpty(textSearch)) {
            strQuery.append(" AND ( s.name LIKE ? OR s.description LIKE ? OR s.location LIKE ? ) ");
            listParam.add("%" + textSearch.trim().toLowerCase() + "%");
            listParam.add("%" + textSearch.trim().toLowerCase() + "%");
            listParam.add("%" + textSearch.trim().toLowerCase() + "%");
        }
        if (Strings.isNullOrEmpty(orderBy)) {
            // if there is no orderBy, then order by start_date
            strQuery.append(" ORDER BY s.update_date_time DESC ");
        } else {
            if (!Strings.isNullOrEmpty(orderType)) {
                // there is orderType, then order by orderBy and orderType
                strQuery.append(" ORDER BY ".concat("s.".concat(orderBy)).concat(" ").concat(orderType));
            } else {
                // there is no orderType, then order by orderBy only
                strQuery.append(" ORDER BY ".concat("s.".concat(orderBy)));
            }
        }
        
        Query query = null;

        if (isCount) {
            query = em.createNativeQuery(strQuery.toString());
        } else {
            query = em.createNativeQuery(strQuery.toString(), Schedule.class);
        }

        for(int i = 0; i < listParam.size(); i++) {
            query.setParameter(i + 1, listParam.get(i));
        }


        if (isCount) {
            BigDecimal count = new BigDecimal((BigInteger) query.getSingleResult());
            lazyLoadDTO.setCount(count);
        } else {
            if(!Strings.isNullOrEmpty(pageSize) && !Strings.isNullOrEmpty(pageNumber)) {
                int page = Integer.parseInt(pageNumber);
                int size = Integer.parseInt(pageSize);
                Pageable pageable = PageRequest.of(page, size);
    
                if(pageable.getPageNumber() - 1 < 0) {
                    query.setFirstResult(0);
                } else {
                    query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
                }
                query.setMaxResults(pageable.getPageSize());
                lazyLoadDTO.setListObject(query.getResultList());
            }
        }

        return lazyLoadDTO;
    }

    @Override
    public ScheduleDTO createOrUpdateSchedule(ScheduleDTO scheduleDTO) {

        Schedule scheduleToSave = null;

        Optional<Schedule> schedule = scheduleRepository.findById(CommonUtils.safeToLong(scheduleDTO.getId()));

        if (!schedule.isPresent()) {
            // create schedule
            scheduleToSave = mapper.mapScheduleFromDTO(scheduleDTO);

            scheduleToSave.setCreateDate(LocalDateTime.now());
            scheduleToSave.setUpdateDate(LocalDateTime.now());
            String userName = scheduleDTO.getUpdateUser();
            scheduleToSave.setCreateUser(userName);
            scheduleToSave.setUpdateUser(userName);

        } else {
            // update schedule

            scheduleToSave = schedule.get();
            scheduleToSave.setUpdateDate(LocalDateTime.now());
            String userName = scheduleDTO.getUpdateUser();
            scheduleToSave.setUpdateUser(userName);
            scheduleToSave.setName(scheduleDTO.getName());
            scheduleToSave.setDescription(scheduleDTO.getDescription());
            scheduleToSave.setLocation(scheduleDTO.getLocation());
            scheduleToSave.setStartDateTime(scheduleDTO.getStartDateTime());
            scheduleToSave.setEndDateTime(scheduleDTO.getEndDateTime());
            // delete all map_schedule for this schedule
            mapScheduleRepository.deleteAllByScheduleId(scheduleToSave.getId());
        }

        // save schedule data to schedule table
        Schedule scheduleSending = scheduleRepository.save(scheduleToSave);

        // if there is an array of categories being sent, then save them to the
        // map_schedule table
        if (!ObjectUtils.isEmpty(scheduleDTO.getCategories())) {

            // loop over the categories id list
            // save each category(id) to the categoryId field of map_schedule table
            // save the schedule id to the scheduleId field of map_schedule table
            List<MapSchedule> mapScheduleList = new ArrayList<MapSchedule>();
            scheduleDTO.getCategories().forEach(categoryId -> {
                Long scheduleId = scheduleSending.getId();
                mapScheduleList.add(new MapSchedule(null, scheduleId, categoryId));
            });

            // loop through the list of map_schedule and add userName to each map_schedule
            mapScheduleList.forEach(mapSchedule -> {
                mapSchedule.setUpdateUser(scheduleSending.getUpdateUser());
                mapSchedule.setCreateUser(scheduleSending.getUpdateUser());
            });

            mapScheduleRepository.saveAll(mapScheduleList);

        }

        // convert data to DTO and add categoriest list
        ScheduleDTO scheduleDTOResult = mapper.mapScheduleFromEntity(scheduleToSave);
        scheduleDTOResult.setCategories(scheduleDTO.getCategories());
        return scheduleDTOResult;

    }



    @Override
    public ScheduleDetailDTO getScheduleDetail(Long id) throws Exception{
        try {
            Optional<Schedule> scheduleDetailWithGivenId = scheduleRepository.findById(CommonUtils.safeToLong(id));

            if (scheduleDetailWithGivenId.isPresent()) {

                ScheduleDetailDTO scheduleDetailResult = mapper.mapScheduleDetailFromEntity(scheduleDetailWithGivenId.get());

                // find all schedule_id in the table MapSchedule
                List<MapSchedule> mapScheduleWithGivenId = mapScheduleRepository.findByScheduleId(CommonUtils.safeToLong(id));

                // if find there is mapScheduleWithGivenId
                if (!CollectionUtils.isEmpty(mapScheduleWithGivenId)) {
                    // find all categories with category_id of each mapScheduleWithGivenId
                    //                List<ScheduleCategories> categoryList = new ArrayList<>();
                    List<Long> categoryIds = new ArrayList<>();

                    // map through the mapScheduleWithGivenId and add category_id to the categoryIds
                    mapScheduleWithGivenId.forEach((item) -> {
                        categoryIds.add(item.getCategoryId());
                    });

                    if (!CollectionUtils.isEmpty(categoryIds)) {
                        // get all categories from the categoryIds list and add it to the response
                        List<ScheduleCategories> categoryList = scheduleCategoriesRepository.findAllById(categoryIds);
                        scheduleDetailResult.setCategories(categoryList);
                    }
                }
                return scheduleDetailResult;
            } else {
                throw new Exception("Schedule not found with id of " + id);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ScheduleDTO> getScheduleBetweenDates(String userName, String fromDate, String toDate) {
//        LocalDateTime fromDateTime = null;
//        LocalDateTime toDateTime = null;
//        String strFormat = "yyyy-MM-dd";
//
//        System.out.println("strFromDate: " + fromDate);
//        System.out.println("strToDate: " + toDate);
//
//        if (!Strings.isNullOrEmpty(fromDate)) {
//            fromDateTime = DateUtil.convertStringToLocalDateTime(fromDate, strFormat);
//        }
//        if (!Strings.isNullOrEmpty(toDate)) {
//            toDateTime = DateUtil.convertStringToLocalDateTime(toDate, strFormat);
//        }
//        if (fromDateTime != null && (toDateTime == null || fromDateTime.isEqual(toDateTime))) {
//            toDateTime = fromDateTime.plusDays(1L);
//        }

        StringBuilder strQuery = new StringBuilder();
        List<Object> listParam = new ArrayList<>();



        strQuery.append("SELECT * FROM schedule s WHERE 1=1 ");

        strQuery.append(" AND start_date_time >= ? ");
        listParam.add(fromDate);

        strQuery.append(" AND end_date_time <= ? ");
        listParam.add(toDate);

        strQuery.append(" AND s.update_user = ? ");
        listParam.add(userName);

        Query query = em.createNativeQuery(strQuery.toString(), Schedule.class);

        for(int i = 0; i < listParam.size(); i++) {
            query.setParameter(i + 1, listParam.get(i));
        }

        List<Schedule> scheduleList = query.getResultList();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleList.forEach(schedule -> {
            scheduleDTOList.add(mapper.mapScheduleFromEntity(schedule));
        });
        return scheduleDTOList;

//        List<Schedule> scheduleList = scheduleRepository.findByUserNameAndBetweenDates(userName, fromDate, toDate);
//        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
//        scheduleList.forEach(schedule -> {
//            scheduleDTOList.add(mapper.mapScheduleFromEntity(schedule));
//        });
//        return scheduleDTOList;
    }
}

