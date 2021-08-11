package br.ml.api.crawler.busca;

import java.io.Serializable;
import java.time.OffsetDateTime;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchedulingTime implements Serializable {
	private static final long serialVersionUID = -1138851586083361905L;

	//@Field(type = FieldType.Long)
	private long hours;
	@Setter(value = AccessLevel.NONE)
	//@Field(type = FieldType.Long)
	private long minutes;
	//@Field(type=FieldType.Date, format = DateFormat.custom, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	//@Field(type=FieldType.Date, format=DateFormat.date_optional_time)
	//@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private OffsetDateTime nextDate;

	public SchedulingTime(int hours, int minutes) {
		super();
		this.hours = hours;
		this.minutes = minutes;
	}

	public SchedulingTime() {
		super();
	}	

	public SchedulingTime(long hora, long minuto, OffsetDateTime schedulingTime) {
		this.hours = hora;
		this.minutes = minuto;
		this.nextDate = schedulingTime;
	}

	/**
	 * Atualizar ou Cria uma nova data e hora para rodar o agendamento
	 * @return
	 */
	public OffsetDateTime nextDateUpdateOrCreate() {
		OffsetDateTime next = OffsetDateTime.now();
		if(this.nextDate == null) {
			return this.nextDate = next;
		}
		if(this.hours != 0L) {
			next = next.plusHours(this.hours);
		}
		if(this.minutes != 0L) {
			next = next.plusMinutes((this.minutes));
		}
		return next;
	}

	public Long validMinutes() {
		if(this.hours == 0L && this.minutes == 0L) {
			this.minutes = minutes == 0L? 20L : minutes;
		}
		return minutes;
	}
}
