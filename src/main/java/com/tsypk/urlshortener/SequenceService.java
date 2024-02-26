package com.tsypk.urlshortener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SequenceService {
    // Сервис для взаимодействия с базой данных, чтобы получить следующий значение сиквенса

    public String generateNextShortCode() {
        // Получите следующее значение сиквенса из базы данных
        long sequenceValue = ...; // логика получения значения из сиквенса

        // Генерация кода на основе айдишника и времени
        String shortCode = ...; // логика генерации кода
        return shortCode;
    }
}

