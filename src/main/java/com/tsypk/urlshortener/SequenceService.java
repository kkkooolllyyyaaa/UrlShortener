package com.tsypk.urlshortener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SequenceService {
    private long sequenceValue = 0; // Эмуляция получения значения из базы данных

    public String generateNextShortCode() {
        long nextId = getNextSequenceValue();
        long currentTimeMillis = Instant.now().toEpochMilli();
        // Преобразование ID и времени в строку на основе базы 36 для сокращения
        String uniquePart = Long.toString(nextId, 36) + Long.toString(currentTimeMillis, 36);
        // Можно дополнительно обработать или укоротить строку, если это необходимо
        return uniquePart;
    }

    private synchronized long getNextSequenceValue() {
        return ++sequenceValue; // Простая имитация инкремента
    }
}


