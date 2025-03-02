package com.example.aston.controller.address;

import com.example.aston.dto.AddressRequestDTO;
import com.example.aston.model.Address;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/address")
@Tag(name = "Address API", description = "API для управления адресами")
public interface AddressController {

    @GetMapping("/all")
    @Operation(summary = "Получить все адреса", description = "Возвращает список всех адресов")
    @ApiResponse(responseCode = "200", description = "Успешный запрос")
    List<AddressRequestDTO> getAllAddresses();

    @GetMapping("/{id}")
    @Operation(summary = "Получить адрес по ID", description = "Возвращает адрес по его уникальному идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос"),
            @ApiResponse(responseCode = "404", description = "Адрес не найден")
    })
    AddressRequestDTO getAddressById(
            @Parameter(description = "Уникальный идентификатор адреса", required = true)
            @PathVariable UUID id);

    @PostMapping("/create")
    @Operation(summary = "Создать новый адрес", description = "Создает новый адрес и возвращает его")
    @ApiResponse(responseCode = "201", description = "Адрес успешно создан")
    AddressRequestDTO createAddress(
            @Parameter(description = "Данные для создания адреса", required = true)
            @RequestBody Address address);

    @PutMapping("/{id}")
    @Operation(summary = "Обновить адрес", description = "Обновляет существующий адрес по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Адрес успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Адрес не найден")
    })
    AddressRequestDTO updateAddress(
            @Parameter(description = "Уникальный идентификатор адреса", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Новые данные для обновления адреса", required = true)
            @RequestBody Address address);

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить адрес", description = "Удаляет адрес по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Адрес успешно удален"),
            @ApiResponse(responseCode = "404", description = "Адрес не найден")
    })
    void deleteAddress(
            @Parameter(description = "Уникальный идентификатор адреса", required = true)
            @PathVariable UUID id);
}