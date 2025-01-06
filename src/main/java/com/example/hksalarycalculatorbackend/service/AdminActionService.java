package com.example.hksalarycalculatorbackend.service;

import com.example.hksalarycalculatorbackend.model.AdminAction;
import com.example.hksalarycalculatorbackend.model.AdminActionType;
import com.example.hksalarycalculatorbackend.repositories.AdminActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminActionService {

    @Autowired
    private AdminActionRepository adminActionRepository;

    public List<AdminAction> getAllAdminActions(){
        List<AdminAction> adminActionList = new ArrayList<>();
        Iterable<AdminAction> adminActionIterable = adminActionRepository.findAll();
        for (AdminAction adminAction : adminActionIterable) {
            adminActionList.add(adminAction);
        }
        return adminActionList;
    }

    public AdminAction createAdminAction(String username, AdminActionType actionType, String ruleName) {
        AdminAction adminAction = AdminAction.builder()
                .username(username)
                .adminActionType(actionType)
                .RuleName(ruleName)
                .timestamp(LocalDateTime.now())
                .build();

        return adminActionRepository.save(adminAction);
    }
}