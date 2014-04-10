package cl.jfoix.atm.comun.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import cl.jfoix.atm.comun.entity.TrabajoSubTipo;

@FacesConverter("TrabajoSubTipoConverter")
public class TrabajoSubTipoConverter implements Converter {

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext fc, UIComponent ui, String str) {
		PickList p = (PickList) ui;
        DualListModel<TrabajoSubTipo> dl = (DualListModel<TrabajoSubTipo>) p.getValue();
        
        for(TrabajoSubTipo wst : dl.getSource()){
        	if(wst.getIdTrabajoSubTipo().toString().equals(str)){
        		return wst;
        	}
        }
        
        for(TrabajoSubTipo wst : dl.getTarget()){
        	if(wst.getIdTrabajoSubTipo().toString().equals(str)){
        		return wst;
        	}
        }
        
        return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent ui, Object obj) {
		return obj == null ? null : ((TrabajoSubTipo) obj).getIdTrabajoSubTipo().toString();
	}

}
