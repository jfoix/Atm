package cl.jfoix.atm.ot.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

@FacesComponent("searchWorks")
public class SearchWorkComponent extends UIInput implements NamingContainer {

	@Override
	public String getFamily() {
		return UINamingContainer.COMPONENT_FAMILY;
	}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		
	}
	 
	@Override
	public Object getSubmittedValue() {
		return null;
	}
	
	@Override
	protected Object getConvertedValue(FacesContext context, Object submittedValue) {
		return null;
	}
}
