package com.novaly.android.proto;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.List;

public class Event implements Item {
	
	public long    m_Id = -1;
	public String  m_Title;
	public String  m_Description;
	public Date    m_Start;
	public Date    m_End;
	public String  m_Place;
	public boolean m_Is_Periodic = false;
	public String  m_PicturePath;
	public String  m_SoundPath;
	
	public Event() { }
	
	@Override
	public TYPE type() {
		return TYPE.TYPE_EVENT;
	}

	@Override
	public String getTitle() {
		return m_Title;
	}

	@Override
	public String getBody() {
		return m_Description;
	}

	@Override
	public Date getDate(MOMENT time_moment) {
		if (time_moment == MOMENT.BEGIN) {
			return m_Start;
		} else if (time_moment == MOMENT.END) {
			return m_End;
		}
		return null;
	}

	@Override
	public boolean isPeriodic() {
		return m_Is_Periodic;
	}

	@Override
	public List<String> getAttachments(TYPE type) {
		ArrayDeque<String> attachList = null;
		if (!m_PicturePath.isEmpty() && !m_SoundPath.isEmpty()) {
			if (type == TYPE.TYPE_PICTURE) {
				attachList = Utils.prepareList(m_PicturePath);
			} else if (type == TYPE.TYPE_SOUND) {
				attachList = Utils.prepareList(m_SoundPath);
			}
		}
		return (List<String>) attachList;
	}
}
