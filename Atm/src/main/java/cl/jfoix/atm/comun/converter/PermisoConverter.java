package cl.jfoix.atm.comun.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import cl.jfoix.atm.login.entity.Permiso;

@FacesConverter("PermisoConverter")
public class PermisoConverter implements Converter {

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext fc, UIComponent ui, String str) {
		PickList p = (PickList) ui;
        DualListModel<Permiso> dl = (DualListModel<Permiso>) p.getValue();
        
        for(Permiso wst : dl.getSource()){
        	if(wst.getIdPermiso().toString().equals(str)){
        		return wst;
        	}
        }
        
        for(Permiso wst : dl.getTarget()){
        	if(wst.getIdPermiso().toString().equals(str)){
        		return wst;
        	}
        }
        
        return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent ui, Object obj) {
		return obj == null ? null : ((Permiso) obj).getIdPermiso().toString();
	}
}
