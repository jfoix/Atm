package cl.jfoix.atm.comun.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import cl.jfoix.atm.comun.entity.TrabajoTipo;

@FacesConverter("TrabajoTipoConverter")
public class TrabajoTipoConverter implements Converter {

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext fc, UIComponent ui, String str) {
		PickList p = (PickList) ui;
        DualListModel<TrabajoTipo> dl = (DualListModel<TrabajoTipo>) p.getValue();
        
        for(TrabajoTipo wst : dl.getSource()){
        	if(wst.getIdTrabajoTipo().toString().equals(str)){
        		return wst;
        	}
        }
        
        for(TrabajoTipo wst : dl.getTarget()){
        	if(wst.getIdTrabajoTipo().toString().equals(str)){
        		return wst;
        	}
        }
        
        return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent ui, Object obj) {
		return obj == null ? null : ((TrabajoTipo) obj).getIdTrabajoTipo().toString();
	}
}
