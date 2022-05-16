package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.dto.ConversationDTO;
import com.buinam.schedulemanger.dto.LazyLoadDTO;
import com.buinam.schedulemanger.model.Chat;
import com.buinam.schedulemanger.model.Message;
import com.buinam.schedulemanger.model.Schedule;
import com.buinam.schedulemanger.repository.ChatRepository;
import com.buinam.schedulemanger.repository.MessageRepository;
import com.buinam.schedulemanger.utils.DateUtil;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private EntityManager em;

    @Override
    public LazyLoadDTO searchMessage(String senderName, String receiverName, String message, String strFromDate, String strToDate, String pageSize, String pageNumber) {
        //find messsage by chat id
        System.out.println("senderName: " + senderName);
        System.out.println("receiverName: " + receiverName);
        Optional<Chat> chatOptional = Optional.ofNullable(chatRepository.findExistedChat(senderName, receiverName));
        Long chatId = null;
        if(chatOptional.isPresent()) {
            System.out.println(chatOptional.get());
            chatId = chatOptional.get().getId();
        } else {
            //throw exception
            throw new RuntimeException("Chat not found");
        }


        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;
        String strFormat = "yyyy-MM-dd";

        if (!Strings.isNullOrEmpty(strFromDate)) {
            fromDate = DateUtil.convertStringToLocalDateTime(strFromDate, strFormat);
        }
        if (!Strings.isNullOrEmpty(strToDate)) {
            toDate = DateUtil.convertStringToLocalDateTime(strToDate, strFormat);
        }
        if (fromDate != null && (toDate == null || fromDate.isEqual(toDate))) {
            toDate = fromDate.plusDays(1L);
        }

        LazyLoadDTO lazyLoadDTO = executeSearchMessageByCondition(chatId, message, fromDate, toDate, pageSize, pageNumber,true );
        if(lazyLoadDTO != null) {

            BigDecimal count = lazyLoadDTO.getCount();

            // if count is greater than 0
            if(count.compareTo(BigDecimal.ZERO) > 0) {
                // get the schedule list
                lazyLoadDTO = executeSearchMessageByCondition(chatId, message, fromDate, toDate, pageSize, pageNumber,false );
                lazyLoadDTO.setCount(count);
            }
        }

        return lazyLoadDTO;

    }

    private LazyLoadDTO executeSearchMessageByCondition(Long chatId, String message, LocalDateTime fromDate, LocalDateTime toDate, String pageSize, String pageNumber, boolean isCount) {
        LazyLoadDTO lazyLoadDTO = new LazyLoadDTO();
        List<Object> listParam = new ArrayList<>();
        StringBuilder strQuery = new StringBuilder();

        if(isCount) {
            strQuery.append("SELECT COUNT(*) FROM Message m WHERE 1=1");
        } else {
            strQuery.append("SELECT * FROM Message m WHERE 1=1");
        }

        strQuery.append(" AND m.chat_id = ?");
        listParam.add(chatId);

        if(fromDate != null) {
            strQuery.append(" AND date >= ? ");
            listParam.add(fromDate);
        }

        if(toDate != null) {
            strQuery.append(" AND date <= ? ");
            listParam.add(toDate);
        }

        if(!Strings.isNullOrEmpty(message)) {
            strQuery.append(" AND message LIKE ? ");
            listParam.add("%" + message + "%");
        }

        strQuery.append(" ORDER BY m.date DESC ");

        Query query = null;

        if (isCount) {
            query = em.createNativeQuery(strQuery.toString());
        } else {
            query = em.createNativeQuery(strQuery.toString(), Message.class);
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
                System.out.println("RESULT LIST: " + query.getResultList());
            }
        }

        return lazyLoadDTO;

    }

    @Override
    public List<ConversationDTO> searchConversations(String senderName) {
        List<Chat> chats = chatRepository.findConversationsWithSenderOrReceiver(senderName);
        if(chats != null && !chats.isEmpty()) {
            List<ConversationDTO> conversations = chats.stream().map(chat -> {
                ConversationDTO conversationDTO = new ConversationDTO();
                conversationDTO.setId(chat.getId());

                if(!chat.getUserOne().equals(chat.getUserTwo()) && !chat.getUserOne().equals(senderName)) {
                    conversationDTO.setUser(chat.getUserOne());
                    conversationDTO.setUnRead(chat.getUnReadOne());
                }
                if(!chat.getUserOne().equals(chat.getUserTwo()) && !chat.getUserTwo().equals(senderName)) {
                    conversationDTO.setUser(chat.getUserTwo());
                    conversationDTO.setUnRead(chat.getUnReadTwo());
                }
                if(chat.getUserOne().equals(chat.getUserTwo())) {
                    conversationDTO.setUser(chat.getUserOne());
                    conversationDTO.setUnRead(0L);
                }
                return conversationDTO;
            }).collect(Collectors.toList());

            return conversations;
        }
        return null;
    }

    @Override
    public Chat seenMessage(String senderName, String receiverName) {
        Optional<Chat> chatOptional = Optional.ofNullable(chatRepository.findExistedChat(senderName, receiverName));

        if(chatOptional.isPresent()) {
            Chat chat = chatOptional.get();
            if(chat.getUserOne().equals(receiverName)) {
                chat.setUnReadOne(0L);
            }
            if(chat.getUserTwo().equals(receiverName)) {
                chat.setUnReadTwo(0L);
            }
            return chatRepository.save(chat);
        } else {
            throw new RuntimeException("Chat not found");
        }
    }

}


