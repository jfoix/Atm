package cl.jfoix.atm.cons.bean;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.WordUtils;

import cl.jfoix.atm.admin.service.IUsuarioService;
import cl.jfoix.atm.comun.excepcion.view.ViewException;
import cl.jfoix.atm.comun.service.IOrdenService;
import cl.jfoix.atm.login.entity.Usuario;
import cl.jfoix.atm.ot.dto.ResumentMecanicoDto;
import cl.jfoix.atm.ot.entity.OrdenTrabajo;

@ViewScoped
@ManagedBean(name="consultaMecanicosMB")
public class ConsultaMecanicosMB implements Serializable {

	private static final long serialVersionUID = -8869306601874238013L;

	@ManagedProperty(value="#{ordenService}")
	private IOrdenService ordenService;
	
	@ManagedProperty(value="#{usuarioService}")
	private IUsuarioService usuarioService;
	
	private Date fechaDesdeBusq;
	private Date fechaHastaBusq;
	private String usuarioMecanicoBusq;
	
	private ResumentMecanicoDto resumenDto;
	private String estadoTrabajo;
	private List<Usuario> mecanicos;
	private List<String> resumenMensual;
	
	@PostConstruct
	public void init(){
		mecanicos = usuarioService.buscarUsuariosMecanicos();
		fechaHastaBusq = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -cal.get(Calendar.DAY_OF_MONTH) + 1);
		cal.add(Calendar.MONTH, -2);
		fechaDesdeBusq = cal.getTime();
	}
	
	public void buscarRendimientoMecanico() throws ViewException{
		
		ViewException vEx = new ViewException();
		
		if(fechaDesdeBusq == null){
			vEx.agregarMensaje("Debe seleccionar una fecha desde");
		}
		if(fechaHastaBusq == null){
			vEx.agregarMensaje("Debe seleccionar una fecha hasta");
		}
		if(fechaDesdeBusq != null && fechaHastaBusq != null && fechaDesdeBusq.after(fechaHastaBusq)){
			vEx.agregarMensaje("La fecha desde debe ser menor a la fecha hasta");
		}
		
		if(vEx.tieneMensajes()){
			throw vEx;
		}
		
		resumenDto = ordenService.consultaTrabajosMecanico(usuarioMecanicoBusq, fechaDesdeBusq, fechaHastaBusq);
		resumenMensual = new ArrayList<String>();
		estadoTrabajo = "";
	}
	
	public void generarResumenMensual(){
		resumenMensual = new ArrayList<String>();
		
		if(!estadoTrabajo.equals("")){
			
			List<OrdenTrabajo> trabajos = null;
			
			if(estadoTrabajo.equals("I")){
				trabajos = resumenDto.getIngresado();
			} else if(estadoTrabajo.equals("P")){
				trabajos = resumenDto.getEnProceso();
			} else if(estadoTrabajo.equals("F")){
				trabajos = resumenDto.getFinalizados();
			} else if(estadoTrabajo.equals("C")){
				trabajos = resumenDto.getCancelados();
			}
			
			if(trabajos != null && trabajos.size() > 0){
				
				Calendar calFechaDesde = Calendar.getInstance();
				calFechaDesde.setTime(fechaDesdeBusq);
				
				Calendar calFechaHasta = Calendar.getInstance();
				calFechaHasta.setTime(fechaHastaBusq);
				
				int mesInicial = calFechaDesde.get(Calendar.MONTH);
				int mesFinal = calFechaHasta.get(Calendar.MONTH);
				
				for(int i = mesInicial; i <= mesFinal; i++){
					int trabajosMes = 0;
					for(OrdenTrabajo ot : trabajos){
						Calendar calOT = Calendar.getInstance();
						calOT.setTime(ot.getOrden().getFechaOrden());
						
						if(calOT.get(Calendar.MONTH) == i){
							trabajosMes++;
						}
					}
					
					if(trabajosMes > 0){
						resumenMensual.add(WordUtils.capitalize((new DateFormatSymbols(new Locale("es", "CL")).getMonths()[i])) + " : " + String.valueOf(trabajosMes));
					}
				}
			}
		}
	}

	/**
	 * @return the ordenService
	 */
	public IOrdenService getOrdenService() {
		return ordenService;
	}

	/**
	 * @param ordenService the ordenService to set
	 */
	public void setOrdenService(IOrdenService ordenService) {
		this.ordenService = ordenService;
	}

	/**
	 * @return the usuarioService
	 */
	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @return the fechaDesdeBusq
	 */
	public Date getFechaDesdeBusq() {
		return fechaDesdeBusq;
	}

	/**
	 * @param fechaDesdeBusq the fechaDesdeBusq to set
	 */
	public void setFechaDesdeBusq(Date fechaDesdeBusq) {
		this.fechaDesdeBusq = fechaDesdeBusq;
	}

	/**
	 * @return the fechaHastaBusq
	 */
	public Date getFechaHastaBusq() {
		return fechaHastaBusq;
	}

	/**
	 * @param fechaHastaBusq the fechaHastaBusq to set
	 */
	public void setFechaHastaBusq(Date fechaHastaBusq) {
		this.fechaHastaBusq = fechaHastaBusq;
	}

	/**
	 * @return the usuarioMecanicoBusq
	 */
	public String getUsuarioMecanicoBusq() {
		return usuarioMecanicoBusq;
	}

	/**
	 * @param usuarioMecanicoBusq the usuarioMecanicoBusq to set
	 */
	public void setUsuarioMecanicoBusq(String usuarioMecanicoBusq) {
		this.usuarioMecanicoBusq = usuarioMecanicoBusq;
	}

	/**
	 * @return the resumenDto
	 */
	public ResumentMecanicoDto getResumenDto() {
		return resumenDto;
	}

	/**
	 * @param resumenDto the resumenDto to set
	 */
	public void setResumenDto(ResumentMecanicoDto resumenDto) {
		this.resumenDto = resumenDto;
	}

	/**
	 * @return the mecanicos
	 */
	public List<Usuario> getMecanicos() {
		return mecanicos;
	}

	/**
	 * @param mecanicos the mecanicos to set
	 */
	public void setMecanicos(List<Usuario> mecanicos) {
		this.mecanicos = mecanicos;
	}

	/**
	 * @return the estadoTrabajo
	 */
	public String getEstadoTrabajo() {
		return estadoTrabajo;
	}

	/**
	 * @param estadoTrabajo the estadoTrabajo to set
	 */
	public void setEstadoTrabajo(String estadoTrabajo) {
		this.estadoTrabajo = estadoTrabajo;
	}

	/**
	 * @return the resumenMensual
	 */
	public List<String> getResumenMensual() {
		return resumenMensual;
	}

	/**
	 * @param resumenMensual the resumenMensual to set
	 */
	public void setResumenMensual(List<String> resumenMensual) {
		this.resumenMensual = resumenMensual;
	}
}
