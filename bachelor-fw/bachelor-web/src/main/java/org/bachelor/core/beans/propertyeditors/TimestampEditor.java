package org.bachelor.core.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.util.StringUtils;

public class TimestampEditor extends PropertyEditorSupport {
	
	private final SimpleDateFormat dateFormat;

	private final boolean allowEmpty;

	private final int exactDateLength;

	public TimestampEditor(SimpleDateFormat dateFormat, boolean allowEmpty) {
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = -1;
	}

	public TimestampEditor(SimpleDateFormat dateFormat, boolean allowEmpty, int exactDateLength) {
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = exactDateLength;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if ((this.allowEmpty) && (!(StringUtils.hasText(text)))) {
			setValue(null);
		} else {
			if ((text != null) && (this.exactDateLength >= 0)
			&& (text.length() != this.exactDateLength)) {
				throw new IllegalArgumentException(
				"Could not parse date: it is not exactly"
				+ this.exactDateLength + "characters long");
			}

			try {
				setValue(new Timestamp(this.dateFormat.parse(text).getTime()));
			} catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: "
				+ ex.getMessage(), ex);
			}
		}
	}

	@Override
	public String getAsText() {
		Timestamp value = (Timestamp) getValue();
		return ((value != null) ? this.dateFormat.format(value) : "");
	}

}
