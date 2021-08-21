package com.go2shop.authentication.model.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.go2shop.common.model.ActiveStatus;

@Converter(autoApply = true)
public class ActiveStatusConverter implements AttributeConverter<ActiveStatus, String> {

	@Override
	public String convertToDatabaseColumn(ActiveStatus status) {
		if (status == null) {
			return null;
		}
		return status.getCode();
	}

	@Override
	public ActiveStatus convertToEntityAttribute(String code) {
		if (code == null) {
			return null;
		}

		return ActiveStatus.getByCode(code);
	}
}