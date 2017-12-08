package com.novaly.android.proto;

import java.util.Date;
import java.util.List;

public class Note implements Item {
	
	public long    m_Id = -1;
	public String  m_Title;
	public Date	   m_CreateDate;
	public Date    m_AlteredDate;
	public String    
	
	public Note() {
		
	}
	
	@Override
	public TYPE type() {
		return null;
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public String getBody() {
		return null;
	}

	@Override
	public Date getDate(MOMENT time_moment) {
		return null;
	}

	@Override
	public boolean isPeriodic() {
		return false;
	}

	@Override
	public List<String> getAttachments(TYPE type) {
		return null;
	}

}
