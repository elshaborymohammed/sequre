package com.ocs.sequre.data.remote.model.request.secondopinion;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionBody.FOR_ME;
import static com.ocs.sequre.data.remote.model.request.secondopinion.SecondOpinionBody.FOR_OTHER;

@IntDef(value = {FOR_ME, FOR_OTHER})
@Retention(RetentionPolicy.SOURCE)
public @interface ForWho { }
