package com.telekom.javaschool.medicalrehapp.service;

import com.telekom.javaschool.medicalrehapp.dao.TimePatternElementRepository;
import com.telekom.javaschool.medicalrehapp.dao.TimePatternRepository;
import com.telekom.javaschool.medicalrehapp.dto.PrescriptionDto;
import com.telekom.javaschool.medicalrehapp.entity.TimeBasis;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternElementEntity;
import com.telekom.javaschool.medicalrehapp.entity.TimePatternEntity;
import com.telekom.javaschool.medicalrehapp.mapper.TimePatternMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TimePatternServiceImpl implements TimePatternService {

    private final TimePatternRepository timePatternRepository;
    private final TimePatternMapper timePatternMapper;
    private final TimePatternElementRepository timePatternElementRepository;

    @Autowired
    public TimePatternServiceImpl(TimePatternRepository timePatternRepository,
                                  TimePatternMapper timePatternMapper,
                                  TimePatternElementRepository timePatternElementRepository) {
        this.timePatternRepository = timePatternRepository;
        this.timePatternMapper = timePatternMapper;
        this.timePatternElementRepository = timePatternElementRepository;
    }

    @Override
    @Transactional
    public TimePatternEntity create(PrescriptionDto prescriptionDto) {
        TimePatternEntity timePatternEntity = timePatternMapper.dtoToEntity(prescriptionDto.getTimePattern());
        setTimePatternElementsToEntity(timePatternEntity);
        return timePatternRepository.save(timePatternEntity);
    }

    private void setTimePatternElementsToEntity(TimePatternEntity timePatternEntity) {
        if (timePatternEntity.getTimeBasis() == TimeBasis.WEEKLY) {
            List<TimePatternElementEntity> timePatternElementList = timePatternEntity.getTimePatternElement();
            timePatternElementList.removeIf(element -> element.getDayOfWeek() == null);
            for (TimePatternElementEntity element : timePatternElementList) {
                element.setTimePattern(timePatternEntity);
                timePatternElementRepository.save(element);
            }
        }
    }
}
