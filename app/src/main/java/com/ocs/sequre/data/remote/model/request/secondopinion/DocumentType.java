package com.ocs.sequre.data.remote.model.request.secondopinion;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.ocs.sequre.data.remote.model.request.secondopinion.MedicalDocumentBody.NEW;
import static com.ocs.sequre.data.remote.model.request.secondopinion.MedicalDocumentBody.LAB;
import static com.ocs.sequre.data.remote.model.request.secondopinion.MedicalDocumentBody.MEDICAL;
import static com.ocs.sequre.data.remote.model.request.secondopinion.MedicalDocumentBody.RADIOLOGY;

@IntDef(value = {NEW,MEDICAL, LAB, RADIOLOGY})
@Retention(RetentionPolicy.SOURCE)
public @interface DocumentType {
}
