package com.lopes.workhours.service;

import com.lopes.workhours.domain.enums.DurationType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class DurationService {

    private final MessageSource messageSource;

    public String getDescription(DurationType durationType, Locale locale) {
        return messageSource.getMessage("duration." + durationType.name().toLowerCase(), null, locale);
    }
}
