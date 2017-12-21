package com.novaly.android.proto;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.List;

public class Note implements Item {
	
	public long    m_Id = -1;
	public String  m_Title = null;
	public Date	   m_CreateDate = null;
	public Date    m_AlteredDate = null;
	public String  m_Body = null;
	public String  m_Attachments = null;
	
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
		
		if (m_Attachments != null && !m_Attachments.isEmpty()) {
			attachList = Utils.prepareList(m_Attachments);
		}
		
		return (List<String>) attachList;
	}

}
