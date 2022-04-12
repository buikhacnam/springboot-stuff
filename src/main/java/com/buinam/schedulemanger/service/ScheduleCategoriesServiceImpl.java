package com.buinam.schedulemanger.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.buinam.schedulemanger.dto.LazyLoadDTO;
import com.buinam.schedulemanger.model.ScheduleCategories;
import com.buinam.schedulemanger.repository.ScheduleCategoriesRepository;
import com.buinam.schedulemanger.utils.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Service
@Transactional
public class ScheduleCategoriesServiceImpl implements ScheduleCategoriesService {
    
    @Autowired
    private ScheduleCategoriesRepository scheduleCategoriesRepository;

    @Autowired
    private EntityManager em;
    
    @Override
    public ScheduleCategories createOrUpdateScheduleCategories(ScheduleCategories scheduleCategories) {
        Optional<ScheduleCategories> scheduleCategoriesResult = scheduleCategoriesRepository.findById(CommonUtils.safeToLong(scheduleCategories.getId()));

        if(!scheduleCategoriesResult.isPresent()) {
            scheduleCategories.setCreateDate(LocalDateTime.now());
            scheduleCategories.setUpdateDate(LocalDateTime.now());
            scheduleCategoriesRepository.save(scheduleCategories);
        } else {
            ScheduleCategories scheduleCategoryUpdate = scheduleCategoriesResult.get();
            scheduleCategoryUpdate.setUpdateDate(LocalDateTime.now());
            scheduleCategoryUpdate.setName(scheduleCategories.getName());
            scheduleCategoryUpdate.setDescription(scheduleCategories.getDescription());
            scheduleCategoryUpdate.setColorSchedule(scheduleCategories.getColorSchedule());
            scheduleCategoriesRepository.save(scheduleCategoryUpdate);
        }
        return scheduleCategories;
    }

    @Override
    public LazyLoadDTO search(String textSeach) {
        LazyLoadDTO lazyLoadDTO = executeSearch(textSeach);
        return lazyLoadDTO;
    }

    @Override
    public ScheduleCategories findCategoryById(Long id) {
        Optional<ScheduleCategories> category = scheduleCategoriesRepository.findById(CommonUtils.safeToLong(id));
        if(category.isPresent()) {
            return category.get();
        } else {
            return null;
        }
    }

    private LazyLoadDTO executeSearch(String textSeach) {
        LazyLoadDTO lazyLoadDTO = new LazyLoadDTO();
        List<Object> listParam = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule_categories WHERE 1=1 ");

        if(textSeach != null && !textSeach.isEmpty()) {
            sql.append(" AND (name LIKE ? OR description LIKE ?) ");
            listParam.add("%" + textSeach + "%");
            listParam.add("%" + textSeach + "%");
        }

        Query query = em.createNativeQuery(sql.toString(), ScheduleCategories.class);
        for(int i = 0; i < listParam.size(); i++) {
            query.setParameter(i + 1, listParam.get(i));
        }

        lazyLoadDTO.setCount(BigDecimal.valueOf(query.getResultList().size()));
        lazyLoadDTO.setListObject(query.getResultList());

        return lazyLoadDTO;
    }
}
    

