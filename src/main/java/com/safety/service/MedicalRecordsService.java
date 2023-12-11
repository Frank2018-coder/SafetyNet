package com.safety.service;

import com.safety.dto.crud.MedicalRecordDTO;

public interface MedicalRecordsService {
    String add(MedicalRecordDTO medicalRecordDTO);

    String update(MedicalRecordDTO medicalRecordDTO);

    String delete(MedicalRecordDTO medicalRecordDTO);
}
