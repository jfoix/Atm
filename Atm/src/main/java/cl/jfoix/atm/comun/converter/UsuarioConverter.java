package cl.jfoix.atm.comun.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import cl.jfoix.atm.login.entity.Usuario;

@FacesConverter("UsuarioConverter")
public class UsuarioConverter implements Converter {

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext fc, UIComponent ui, String str) {
		PickList p = (PickList) ui;
        DualListModel<Usuario> dl = (DualListModel<Usuario>) p.getValue();
        
        for(Usuario wst : dl.getSource()){
        	if(wst.getNombreUsuario().equals(str)){
        		return wst;
        	}
        }
        
        for(Usuario wst : dl.getTarget()){
        	if(wst.getNombreUsuario().equals(str)){
        		return wst;
        	}
        }
        
        return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent ui, Object obj) {
		return obj == null ? null : ((Usuario) obj).getNombreUsuario();
	}
}
