package com.example.hksalarycalculatorbackend.Service;

import com.example.hksalarycalculatorbackend.model.AdminAction;
import com.example.hksalarycalculatorbackend.model.AdminActionType;
import com.example.hksalarycalculatorbackend.repositories.AdminActionRepository;
import com.example.hksalarycalculatorbackend.service.AdminActionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestAdminActionService {

    @InjectMocks
    private AdminActionService adminActionService;

    @Mock
    private AdminActionRepository adminActionRepository;

    @Test
    void getAllAdminActions_ShouldReturnListOfAdminActions() {
        // Arrange
        AdminAction action1 = AdminAction.builder()
                .username("admin1")
                .adminActionType(AdminActionType.CREATED)
                .RuleName("Rule1")
                .timestamp(LocalDateTime.now())
                .build();

        AdminAction action2 = AdminAction.builder()
                .username("admin2")
                .adminActionType(AdminActionType.UPDATED)
                .RuleName("Rule2")
                .timestamp(LocalDateTime.now())
                .build();

        when(adminActionRepository.findAll()).thenReturn(Arrays.asList(action1, action2));

        // Act
        List<AdminAction> result = adminActionService.getAllAdminActions();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("admin1", result.get(0).getUsername());
        assertEquals(AdminActionType.CREATED, result.get(0).getAdminActionType());

        verify(adminActionRepository, times(1)).findAll();
    }

    @Test
    void createAdminAction_ShouldReturnSavedAdminAction() {
        // Arrange
        AdminAction action = AdminAction.builder()
                .username("admin1")
                .adminActionType(AdminActionType.CREATED)
                .RuleName("New Rule")
                .timestamp(LocalDateTime.now())
                .build();

        when(adminActionRepository.save(any(AdminAction.class))).thenReturn(action);

        // Act
        AdminAction result = adminActionService.createAdminAction("admin1", AdminActionType.CREATED, "New Rule");

        // Assert
        assertNotNull(result);
        assertEquals("admin1", result.getUsername());
        assertEquals(AdminActionType.CREATED, result.getAdminActionType());
        assertEquals("New Rule", result.getRuleName());

        verify(adminActionRepository, times(1)).save(any(AdminAction.class));
    }
}

