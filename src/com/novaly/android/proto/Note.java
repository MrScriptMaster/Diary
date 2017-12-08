package com.novaly.android.proto;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.List;

public class Note implements Item {
	
	public long    m_Id = -1;
	public String  m_Title;
	public Date	   m_CreateDate;
	public Date    m_AlteredDate;
	public String  m_Body;
	public String  m_Attachments;
	
	public Note() {
		
	}
	
	@Override
	public TYPE type() {
		return TYPE.TYPE_NOTE;
	}

	@Override
	public String getTitle() {
		return m_Title;
	}

	@Override
	public String getBody() {
		return m_Body;
	}
	
	/**
	 * @return Для MOMENT.BEGIN возвращает время создания; для 
	 * MOMENT.END возвращает время последнего редактирования.
	 */
	@Override
	public Date getDate(MOMENT time_moment) {
		if (time_moment == MOMENT.BEGIN) {
			return m_CreateDate;
		}
		return m_AlteredDate;
	}

	@Override
	public boolean isPeriodic() {
		return false;
	}

	@Override
	public List<String> getAttachments(TYPE type) {
		ArrayDeque<String> attachList = null;
		
		if (!m_Attachments.isEmpty()) {
			attachList = Utils.prepareList(m_Attachments);
		}
		
		return (List<String>) attachList;
	}

}
