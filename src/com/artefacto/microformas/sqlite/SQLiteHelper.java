package com.artefacto.microformas.sqlite;

import java.util.Calendar;

import com.artefacto.microformas.beans.AlmacenesBean;
import com.artefacto.microformas.beans.CalidadBilleteBean;
import com.artefacto.microformas.beans.CambiosStatusBean;
import com.artefacto.microformas.beans.CausaRetiroBean;
import com.artefacto.microformas.beans.CausasBean;
import com.artefacto.microformas.beans.CausasRechazoBean;
import com.artefacto.microformas.beans.ClientBean;
import com.artefacto.microformas.beans.ClienteCalidadBilleteBean;
import com.artefacto.microformas.beans.ClienteCondicionSiteBean;
import com.artefacto.microformas.beans.ClienteModelosBean;
import com.artefacto.microformas.beans.ClientesCambiosStatusBean;
import com.artefacto.microformas.beans.CodigosIntervencion0Bean;
import com.artefacto.microformas.beans.CodigosIntervencion1Bean;
import com.artefacto.microformas.beans.CodigosIntervencion2Bean;
import com.artefacto.microformas.beans.CondicionSiteBean;
import com.artefacto.microformas.beans.ConnectivityBean;
import com.artefacto.microformas.beans.DireccionesBean;
import com.artefacto.microformas.beans.EspecificaCausasRechazoBean;
import com.artefacto.microformas.beans.EspecificacionTipoFallaBean;
import com.artefacto.microformas.beans.EtiquetasBean;
import com.artefacto.microformas.beans.FailsFoundBean;
import com.artefacto.microformas.beans.GruposBean;
import com.artefacto.microformas.beans.GruposClientesBean;
import com.artefacto.microformas.beans.IngenierosBean;
import com.artefacto.microformas.beans.InsumosBean;
import com.artefacto.microformas.beans.MarcasBean;
import com.artefacto.microformas.beans.MSparePartsBean;
import com.artefacto.microformas.beans.ModelosBean;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.beans.ProductosStatusBean;
import com.artefacto.microformas.beans.PackageShipmentBean;
import com.artefacto.microformas.beans.RequestDetailBean;
import com.artefacto.microformas.beans.SKUBean;
import com.artefacto.microformas.beans.ServiciosBean;
import com.artefacto.microformas.beans.ServiciosCausasBean;
import com.artefacto.microformas.beans.ServiciosSolucionesBean;
import com.artefacto.microformas.beans.SoftwareBean;
import com.artefacto.microformas.beans.SolucionesBean;
import com.artefacto.microformas.beans.SparePartsBean;
import com.artefacto.microformas.beans.StatusBean;
import com.artefacto.microformas.beans.SupplyBean;
import com.artefacto.microformas.beans.TipoFallaBean;
import com.artefacto.microformas.beans.UnitBean;
import com.artefacto.microformas.beans.UnidadesBean;
import com.artefacto.microformas.beans.ViaticosBean;
import com.artefacto.microformas.constants.Constants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper  extends SQLiteOpenHelper
{   //Constantes a utilizar para procesos CIUD
    public static final int DATABASE_VERSION = 21;
    public static final String DATABASE_NAME = "MicroformasDB";

    public static final String CLIENTS_DB_NAME = "clientes";
    public static final String CLIENTS_ID_CLIENT = "id_cliente";
    public static final String CLIENTS_DESC_CLIENT = "description";
    public static final String CLIENTS_MOBILE = "activate_mobile_notification";

    public static final String INV_SUPPLIES_DB_NAME = "inv_supplies";
    public static final String INV_SUPPLIES_ID_SUPPLY = "id_insumo";
    public static final String INV_SUPPLIES_DESC_CLIENT = "desc_cliente";
    public static final String INV_SUPPLIES_DESC = "desc";
    public static final String INV_SUPPLIES_TOTAL = "total";

    public static final String CATALOGS_DB_NAME = "catalogs";
    public static final String CATALOGS_ID_CATALOG = "id_catalog";
    //	public static final String CATALOGS_DESC 				= "desc";
    public static final String CATALOGS_NUMBER = "number";
    public static final String CATALOGS_MD5 = "md5";

    //Constantes a utilizar para procesos CIUD
    public static final String REQUESTS_DB_NAME = "requests";
    public static final String REQUESTS_ID_REQUEST = "id_request";
    public static final String REQUESTS_IS_KEY_ACCOUNT = "is_key_account";
    public static final String REQUESTS_HORAS_GARANTIA = "horas_garantia";
    public static final String REQUESTS_HORAS_ATENCION = "horas_atencion";
    public static final String REQUESTS_FEC_ALTA = "fec_alta";
    public static final String REQUESTS_FEC_ATENCION = "fec_atencion";
    public static final String REQUESTS_FEC_GARANTIA = "fec_garantia";
    public static final String REQUESTS_FEC_CIERRE = "fec_cierre";
    public static final String REQUESTS_ID_PRODUCTO = "id_producto";
    public static final String REQUESTS_ID_UNIDAD_ATENDIDA = "id_unidad_atendida";
    public static final String REQUESTS_ID_MODELO = "id_modelo";
    public static final String REQUESTS_ID_SERVICIO = "id_servicio";
    public static final String REQUESTS_ID_NEGOCIO = "id_negocio";
    public static final String REQUESTS_NO_AR = "no_ar";
    public static final String REQUESTS_DESC_CLIENTE = "desc_cliente";
    public static final String REQUESTS_NO_AFILIACION = "no_afiliacion";
    public static final String REQUESTS_DESC_SERVICIO = "desc_servicio";
    public static final String REQUESTS_SINTOMA = "sintoma";
    public static final String REQUESTS_CONCEPTO = "concepto";
    public static final String REQUESTS_DESC_CORTA = "desc_corta";
    public static final String REQUESTS_BITACORA = "bitacora";
    public static final String REQUESTS_NOTAS_REMEDY = "notas_remedy";
    public static final String REQUESTS_DESC_EQUIPO = "desc_equipo";
    public static final String REQUESTS_EQUIPO = "equipo";
    public static final String REQUESTS_NO_SERIE = "no_serie";
    public static final String REQUESTS_DIRECCION = "direccion";
    public static final String REQUESTS_COLONIA = "colonia";
    public static final String REQUESTS_POBLACION = "poblacion";
    public static final String REQUESTS_ESTADO = "estado";
    public static final String REQUESTS_CP = "cp";
    public static final String REQUESTS_DESC_NEGOCIO = "desc_negocio";
    public static final String REQUESTS_TELEFONO = "telefono";
    public static final String REQUESTS_CAJA = "caja";
    public static final String REQUESTS_PREFACTURACION = "v_prefacturacion";
    public static final String REQUESTS_COMENTARIO = "v_comentario";
    public static final String REQUESTS_NO_SIM = "no_sim";
    public static final String REQUESTS_CLAVE_RECHAZO = "clave_rechazo";
    public static final String REQUESTS_ID_FALLA = "id_falla";
    public static final String REQUESTS_DESC_SOFTWARE = "desc_software";
    public static final String REQUESTS_DESC_CONN = "desc_connectivity";
    public static final String REQUEST_DESC_C_RETIRO = "desc_causa_retiro";
    public static final String REQUESTS_ID_CLIENTE = "id_cliente";

    public static final String STATUS_DB_NAME = "status";
    public static final String STATUS_ID_STATUS = "id_status";
    public static final String STATUS_IS_NUEVAS = "is_nuevas";
    public static final String STATUS_IS_CERRADAS = "is_cerradas";
    public static final String STATUS_IS_PENDIENTES = "is_pendientes";
    public static final String STATUS_IS_ABIERTAS = "is_abiertas";
    public static final String STATUS_IS_SOLICITUD_ALMACEN = "is_solicitud_almacen";
    public static final String STATUS_IS_SOLICITUD_VIATICOS = "is_solicitud_viaticos";
    public static final String STATUS_DESC_STATUS = "desc_status";
    public static final String STATUS_ACTIVO = "activo";
    public static final String STATUS_ORDEN = "orden";
    public static final String STATUS_MOBILE = "activate_mobile_notification";

    public static final String EXTRA_DB_NAME = "extra_catalogs";
    public static final String EXTRA_ID_EXTRA = "id_extra";
    public static final String EXTRA_DESC = "desc";
    public static final String EXTRA_DATE = "date";

    public static final String CAMBIO_DB_NAME = "cambio_status";
    public static final String CAMBIO_STATUS_AR_INI = "status_ini";
    public static final String CAMBIO_STATUS_AR_FIN = "status_fin";

    public static final String PRODUCTO_DB_NAME = "productos_status";
    public static final String PRODUCTO_ID_PRODUCTO = "id_producto";

    public static final String CLIENTES_DB_NAME = "clientes_cambio";
    public static final String CLIENTES_ID_CLIENTE = "id_cliente";

    public static final String LISTAVIATICOS_DB_NAME = "lista_viaticos";
    public static final String LISTAVIATICOS_CONCEPTO = "concepto";
    public static final String LISTAVIATICOS_COSTO = "costo";

    public static final String VIATICOS_DB_NAME = "viaticos";
    public static final String VIATICOS_ID_VIATICO = "id_viatico";
    public static final String VIATICOS_DESC_VIATICO = "desc_viatico";

    public static final String SPAREPARTS_DB_NAME = "spare_parts";
    public static final String SPAREPARTS_ID_SPAREPART = "id_spare_part";
    public static final String SPAREPARTS_DESC_SPAREPART = "desc_spare_part";

    public static final String INSUMOS_DB_NAME = "insumos";
    public static final String INSUMOS_ID_INSUMO = "id_insumo";
    public static final String INSUMOS_ID_CLIENTE = "id_cliente";
    public static final String INSUMOS_ID_TIPO_INSUMO = "id_tipo_insumos";
    public static final String INSUMOS_DESC_INSUMO = "desc_insumo";

    public static final String ALMACENES_DB_NAME = "almacenes";
    public static final String ALMACENES_ID_ALMACEN = "id_almacen";
    public static final String ALMACENES_DESC_ALMACEN = "desc_almacen";

    public static final String MARCAS_DB_NAME = "marcas";
    public static final String MARCAS_ID_MARCA = "id_marca";
    public static final String MARCAS_DESC_MARCA = "desc_marca";

    public static final String MSPAREPARTS_DB_NAME = "mspare_parts";
    public static final String MSPAREPARTS_ID_MSPAREPART = "id_mspare_part";

    public static final String MODELOS_DB_NAME = "modelos";
    public static final String MODELOS_ID_MODELO = "id_modelo";
    public static final String MODELOS_ID_GPRS = "id_gprs";
    public static final String MODELOS_DESC_MODELO = "desc_modelo";
    public static final String MODELOS_SKU = "sku";

    public static final String DIRECCIONES_DB_NAME = "direcciones";
    public static final String DIRECCIONES_ID_DIRECCION = "id_direccion";
    public static final String DIRECCIONES_DIRECCION = "direccion";
    public static final String DIRECCIONES_COLONIA = "colonia";
    public static final String DIRECCIONES_POBLACION = "poblacion";
    public static final String DIRECCIONES_ESTADO = "estado";
    public static final String DIRECCIONES_IS_DEFAULT = "isdefault";

    public static final String SERVICES_DB_NAME = "servicios";
    public static final String SERVICES_ID_SERVICIO = "id_servicio";
    public static final String SERVICES_ID_TIPO_SERVICIO = "id_tipo_servicio";
    public static final String SERVICES_ID_MONEDA = "id_moneda";
    public static final String SERVICES_ID_TIPO_PRECIO = "id_tipo_precio";
    public static final String SERVICES_IS_INS_REQ = "is_insumos_required";
    public static final String SERVICES_IS_CAU_SOL_REQ = "is_causa_solucion_required";
    public static final String SERVICES_IS_CAU_REQ = "is_causa_required";
    public static final String SERVICES_IS_SOL_REQ = "is_solucion_required";
    public static final String SERVICES_IS_TAS_REQ = "is_tas_required";
    public static final String SERVICES_IS_OTOR_TAS_REQ = "is_otorgante_tas_required";
    public static final String SERVICES_IS_NO_EQUIPO_REQ = "is_no_equipo_required";
    public static final String SERVICES_IS_NO_SERIE_REQ = "is_no_serie_required";
    public static final String SERVICES_IS_NO_INV_REQ = "is_no_inventario_required";
    public static final String SERVICES_ID_IS_MODELO_REQ = "id_modelo_required";
    public static final String SERVICES_IS_FEC_LLEG_REQ = "is_fec_llegada_required";
    public static final String SERVICES_IS_FEC_LLE_TER_REQ = "is_fec_llegada_terceros_required";
    public static final String SERVICES_IS_FOLIO_SER_REQ = "is_folio_servicio_required";
    public static final String SERVICES_IS_FEC_INI_ING_REQ = "is_fec_ini_ingeniero_required";
    public static final String SERVICES_IS_FEC_FIN_ING_REQ = "is_fec_fin_ingeniero_required";
    public static final String SERVICES_IS_OTOR_VOBO_REQ = "is_otorgante_vobo_required";
    public static final String SERVICES_IS_OT_VOBO_TER_REQ = "is_otorgante_vobo_terceros_required";
    public static final String SERVICES_IS_INT_SEN_REQ = "is_intensidad_senial_required";
    public static final String SERVICES_IS_SIM_REEM_REQ = "is_sim_reemplazada_required";
    public static final String SERVICES_IS_FOL_SER_REC_REQ = "is_folio_servicio_rechazo_required";
    public static final String SERVICES_IS_OT_VOBO_REC_REQ = "is_otorgante_vobo_rechazo_required";
    public static final String SERVICES_IS_FALLA_ENC_REQ = "is_falla_encontrada_required";
    public static final String SERVICES_IS_OT_VOBO_CLI_REQ = "is_otorgante_vobo_cliente_required";
    public static final String SERVICES_IS_MOT_COBRO_REQ = "is_motivo_cobro_required";
    public static final String SERVICES_IS_SOPOR_CLI_REQ = "is_soporte_cliente_required";
    public static final String SERVICES_IS_OT_SOP_CLI_REQ = "is_otorgante_soporte_cliente_required";
    public static final String SERVICES_IS_BOLETIN_REQ = "is_boletin_required";
    public static final String SERVICES_IS_CAD_CIE_ESC_REQ = "is_cadena_cierre_escrita_required";
    public static final String SERVICES_IS_DOWNTIME = "is_downtime";
    public static final String SERVICES_IS_CIERRE_PDA = "is_cierre_pda";
    public static final String SERVICES_IS_APLICACION_REQ = "is_aplicacion_required";
    public static final String SERVICES_IS_VERSION_REQ = "is_version_required";
    public static final String SERVICES_IS_CAJA_REQ = "is_caja_required";
    public static final String SERVICES_DESC_SERVICIO = "desc_servicio";
    public static final String SERVICES_ID_AR_REOPEN = "IdARReopen";
    public static final String SERVICES_ID_AR_NEED_FILE = "IdARNeedFile";
    public static final String SERVICES_ID_AR_NO_CHECKUP = "IdARNeedNOcheckup";
    public static final String SERVICES_IS_KIT_REQ = "need_kit";
    public static final String SERVICES_KIT_SUPPLY = "kit_insumo";
    public static final String SERVICES_NEED_SHEET = "need_sheet";
    public static final String SERVICES_CALIDAD_BILLETE = "calidad_billete";
    public static final String SERVICES_CONDICION_SITE = "condicion_site";

    public static final String UNIDADES_DB_NAME = "unidades";
    public static final String UNIDADES_ID_UNIDAD = "id_unidad";
    public static final String UNIDADES_ID_STATUS_UNIDAD = "id_status_unidad";

    public static final String PROCESS_SHIPMENT_DB_NAME = "envios_pendientes";
    public static final String PROCESS_SHIPMENT_ID = "id_envio_pendiente";
    public static final String PROCESS_SHIPMENT_TYPE = "type";
    public static final String PROCESS_SHIPMENT_COUNT = "count";

    public static final String RECEPTIONS_DB_NAME = "recepciones";
    public static final String RECEPTIONS_ID_SHIPMENT = "id_envio";
    public static final String RECEPTIONS_TYPE = "type";
    public static final String RECEPTIONS_COUNT = "count";

    public static final String UNIDAD_DB_NAME = "unidad";
    public static final String UNIDAD_ID_SOL_REC = "id_solicitud_recoleccion";
    public static final String UNIDAD_IS_NUEVA = "is_nueva";
    public static final String UNIDAD_IS_DANIADA = "is_daniada";
    public static final String UNIDAD_IS_RETIRO = "is_retiro";
    public static final String UNIDAD_ID_TIPO_PRODUCTO = "id_tipo_producto";
    public static final String UNIDAD_ID_USUARIO_ALTA = "id_usuario_alta";
    public static final String UNIDAD_DESC_CLIENTE = "desc_cliente";
    public static final String UNIDAD_DESC_MARCA = "desc_marca";
    public static final String UNIDAD_DESC_MODELO = "desc_modelo";
    public static final String UNIDAD_NO_SERIE = "no_serie";
    public static final String UNIDAD_NO_INVENTARIO = "no_inventario";
    public static final String UNIDAD_NO_IMEI = "no_imei";
    public static final String UNIDAD_NO_SIM = "no_sim";
    public static final String UNIDAD_NO_EQUIPO = "no_equipo";
    public static final String UNIDAD_DESC_LLAVE = "desc_llave";
    public static final String UNIDAD_DESC_SOFTWARE = "desc_software";
    public static final String UNIDAD_POSICION_INVENTARIO = "posicion_inventario";
    public static final String UNIDAD_DESC_STATUS_UNIDAD = "desc_status_unidad";
    public static final String UNIDAD_STATUS = "status";
    public static final String UNIDAD_FEC_ALTA = "fec_alta";
    public static final String UNIDAD_DESC_ACCESORIO = "desc_accesorio";
    public static final String UNIDAD_DESCRIPCION_ACC = "descripcion_acc";
    public static final String UNIDAD_STATUS_ACCESORIO = "status_accesorio";
    public static final String UNIDAD_CONN = "desc_connectivity";

    public static final String CAUSAS_DB_NAME = "causas";
    public static final String CAUSAS_ID_CAUSA = "id_causa";
    public static final String CAUSAS_ID_CLIENTE = "id_cliente";
    public static final String CAUSAS_CLAVE = "clave";
    public static final String CAUSAS_DESC_CAUSA = "desc_causa";

    public static final String SOLUCIONES_DB_NAME = "soluciones";
    public static final String SOLUCIONES_ID_SOLUCION = "id_solucion";
    public static final String SOLUCIONES_ID_CLIENTE = "id_cliente";
    public static final String SOLUCIONES_CLAVE = "clave";
    public static final String SOLUCIONES_IS_EXITO = "is_exito";
    public static final String SOLUCIONES_DESC_SOLUCION = "desc_solucion";

    public static final String CAUREC_DB_NAME = "causas_rechazo";
    public static final String CAUREC_ID_CAUREC = "id_causa_rechazo";
    public static final String CAUREC_DESC_CAUREC = "desc_causa_rechazo";

    public static final String ESPCAUREC_DB_NAME = "especificacion_causas_rechazo";
    public static final String ESPCAUREC_ID_ESPCAUREC = "id_especificacion_causa_rechazo";
    public static final String ESPCAUREC_ID_CAURECPARENT = "id_causa_rechazo_parent";
    public static final String ESPCAUREC_DESC_ESPCAUREC = "desc_especificacion_causa_rechazo";

    public static final String GRUPOS_DB_NAME = "grupos";
    public static final String GRUPOS_ID_GRUPO = "id_grupos";
    public static final String GRUPOS_DESC_GRUPO = "desc_grupos";

    public static final String GRUPOSCLI_DB_NAME = "grupos_clientes";
    public static final String GRUPOSCLI_ID_GRUPOCLI = "id_grupo_clientes";

    public static final String SERVCAU_DB_NAME = "servicios_causas";
    public static final String SERVCAU_ID_SERVCAU = "id_servicio_causa";

    public static final String SERVSOL_DB_NAME = "servicios_soluciones";
    public static final String SERVSOL_ID_SERVSOL = "id_servicio_solucion";

    public static final String CLIMOD_DB_NAME = "clientes_modelos";
    public static final String CLIMOD_ID_CLIMOD = "id_cliente_modelo";

    public static final String CODIGOS0_DB_NAME = "codigos_intervencion_0";
    public static final String CODIGOS0_ID_CODIGO = "id_codigo_0";
    public static final String CODIGOS0_CLAVE_CODIGO = "clave_codigo";
    public static final String CODIGOS0_REPORTA_INSTALACION = "reporta_instalacion";
    public static final String CODIGOS0_DESC_CODIGO = "desc_codigo";

    public static final String CODIGOS1_DB_NAME = "codigos_intervencion_1";
    public static final String CODIGOS1_ID_CODIGO = "id_codigo_1";
    public static final String CODIGOS1_CLAVE_CODIGO = "clave_codigo";
    public static final String CODIGOS1_ID_PARENT_0 = "id_parent_0";
    public static final String CODIGOS1_DESC_CODIGO = "desc_codigo";

    public static final String CODIGOS2_DB_NAME = "codigos_intervencion_2";
    public static final String CODIGOS2_ID_CODIGO = "id_codigo_2";
    public static final String CODIGOS2_CLAVE_CODIGO = "clave_codigo";
    public static final String CODIGOS2_ID_PARENT_1 = "id_parent_1";
    public static final String CODIGOS2_DESC_CODIGO = "desc_codigo";

    public static final String TIPOFALLA_DB_NAME = "tipo_falla";
    public static final String TIPOFALLA_ID_TIPOFALLA = "id_tipo_falla";
    public static final String TIPOFALLA_DESC_TIPOFALLA = "desc_tipo_falla";

    public static final String ESPTIFA_DB_NAME = "especificacion_tipo_falla";
    public static final String ESPTIFA_ID_ESPTIFA = "id_especificacion_tipo_falla";
    public static final String ESPTIFA_ID_FALLA_PARENT = "id_falla_parent";
    public static final String ESPTIFA_DESC_ESPTIFA = "desc_especificacion_tipo_falla";

    public static final String LISTAUNIDADES_DB_NAME = "temp_unidad";
    public static final String LISTAUNIDADES_DESC_UNIDAD = "desc_unidad";

    public static final String INGENIEROS_DB_NAME = "ingenieros";
    public static final String INGENIEROS_ID_INGENIERO = "id_ingeniero";
    public static final String INGENIEROS_NOMBRECOMPLETO = "nombre_completo";

    public static final String CONNECTIVITY_DB_NAME = "conectividad";
    public static final String CONNECTIVITY_ID = "id";
    public static final String CONNECTIVITY_ID_CLIENT = "id_cliente";
    public static final String CONNECTIVITY_DESC = "descripcion";
    public static final String CONNECTIVITY_IS_GPRS = "is_gprs";

    /*
    * Catalogo Causas Retiros 22/03/2017
    * */
    public static final String CAUSA_RETIRO_DB_NAME    = "causa_retiro";
    public static final String CAUSA_RETIRO_ID         = "id";
    public static final String CAUSA_RETIRO_ID_CLIENTE = "id_cliente";
    public static final String CAUSA_RETIRO_DESC       = "descripcion";
    public static final String CAUSA_RETIRO_STATUS     = "status";

    /*
        Inicia cambio agrager catalogo de etiquetas 16/08/2017
     */
    public static final String ETIQUETAS_BD_NAME    = "etiquetas";
    public static final String ETIQUETAS_ID         = "id";
    public static final String ETIQUETAS_ID_CLIENTE = "id_cliente";
    public static final String ETIQUETAS_DESC       = "descripcion";

    public static final String SOFTWARE_DB_NAME = "software";
    public static final String SOFTWARE_ID = "id";
    public static final String SOFTWARE_ID_CLIENT = "id_cliente";
    public static final String SOFTWARE_DESC = "descripcion";

    public static final String SKU_DB_NAME = "sku";
    public static final String SKU_ID_SKU = "id_sku";
    public static final String SKU_DESC = "descripcion";

    public static final String DOCUMENTS_DB_NAME = "DOCUMENTS";
    public static final String DOCUMENTS_FECHA = "fecha";

    public static final String FAILS_FOUND_DB_NAME = "fallas_encontradas";
    public static final String FAILS_FOUND_ID = "id_falla";
    public static final String FAILS_FOUND_DESC = "desc_falla";
    public static final String FAILS_FOUND_ID_FATHER = "id_padre";
    public static final String FAILS_FOUND_ID_CLIENT = "id_cliente";

    /*JDOS AGREGA NUEVOS CATALOGOS 30/01/2018*/

    public static final String CALIDAD_BILLETE = "calidad_billete";
    public static final String CALIDAD_BILLETE_ID = "id_calidad_billete";
    public static final String CALIDAD_BILLETE_DESC = "desc_calidad_billete";

    public static final String CLIENTE_CALIDAD_BILLETE = "cliente_calidad_billete";
    public static final String CLIENTE_CALIDAD_BILLETE_ID = "id_cliente_calidad_billete";
    public static final String CLIENTE_CALIDAD_BILLETE_ID_CLIENTE = "id_cliente";
    public static final String CLIENTE_CALIDAD_BILLETE_ID_CALIDAD = "id_calidad_billete";

    public static final String CONDICIONES_SITE = "condiciones_site";
    public static final String CONDICIONES_SITE_ID = "id_condiciones_site";
    public static final String CONDICIONES_SITE_DESC = "desc_condiciones_site";

    public static final String CLIENTE_CONDICIONES_SITE = "cliente_condiciones_site";
    public static final String CLIENTE_CONDICIONES_SITE_ID = "id_cliente_condiciones_site";
    public static final String CLIENTE_CONDICIONES_SITE_ID_CLIENTE = "id_cliente";
    public static final String CLIENTE_CONDICIONES_SITE_ID_CONDICION = "id_condicion_site";

    private final String createCalidadBillete = "CREATE TABLE " + CALIDAD_BILLETE +" (" + CALIDAD_BILLETE_ID + " INTEGER NOT NULL, " + CALIDAD_BILLETE_DESC + " TEXT)";
    private final String createCondicionesSite = "CREATE TABLE " + CONDICIONES_SITE +" (" + CONDICIONES_SITE_ID + " INTEGER NOT NULL, " + CONDICIONES_SITE_DESC + " TEXT)";
    private final String createClienteCalidadBillete = "CREATE TABLE " + CLIENTE_CALIDAD_BILLETE +" (" + CLIENTE_CALIDAD_BILLETE_ID + " INTEGER NOT NULL, "
            + CLIENTE_CALIDAD_BILLETE_ID_CLIENTE + " INTEGER," + CLIENTE_CALIDAD_BILLETE_ID_CALIDAD +" INTEGER)";
    private final String createClienteCondicionesSite = "CREATE TABLE " + CLIENTE_CONDICIONES_SITE +" (" + CLIENTE_CONDICIONES_SITE_ID + " INTEGER NOT NULL, "
            + CLIENTE_CONDICIONES_SITE_ID_CLIENTE + " INTEGER," + CLIENTE_CONDICIONES_SITE_ID_CONDICION +" INTEGER)";
    /*FIN*/

    private final String createCatalogs = "CREATE TABLE catalogs(id_catalog INTEGER PRIMARY KEY NOT NULL, desc TEXT, number INTEGER, md5 TEXT)";

    private final String createOtherCatalogs = "CREATE TABLE extra_catalogs(id_extra INTEGER PRIMARY KEY NOT NULL, desc TEXT, date DATE)";

    private final String createStatusCatalog = "CREATE TABLE status(id_status INTEGER PRIMARY KEY NOT NULL, is_nuevas INTEGER, is_cerradas INTEGER,"
            + "is_pendientes INTEGER, is_abiertas INTEGER, is_solicitud_almacen INTEGER, is_solicitud_viaticos INTEGER, desc_status TEXT, "
            + "activo TEXT, orden INTEGER, activate_mobile_notification INTEGER)";

    private final String createCambioCatalog = "CREATE TABLE " + CAMBIO_DB_NAME + "(" + CLIENTES_ID_CLIENTE + " INTEGER, "
            + CAMBIO_STATUS_AR_INI + " INTEGER, "
            + CAMBIO_STATUS_AR_FIN + " INTEGER)";

    private final String createProductosCatalog = "CREATE TABLE " + PRODUCTO_DB_NAME + "(" + PRODUCTO_ID_PRODUCTO + " INTEGER NOT NULL, "
            + STATUS_ID_STATUS + " INTEGER)";

    private final String createClientesCatalog = "CREATE TABLE " + CLIENTES_DB_NAME + "(" + CLIENTES_ID_CLIENTE + " INTEGER)";

    private final String createListaViaticosCatalog = "CREATE TABLE " + LISTAVIATICOS_DB_NAME + "(" + VIATICOS_ID_VIATICO + " INTEGER, "
            + LISTAVIATICOS_CONCEPTO + " TEXT, "
            + LISTAVIATICOS_COSTO + " TEXT)";

    private final String createViaticosCatalog = "CREATE TABLE " + VIATICOS_DB_NAME + "(" + VIATICOS_ID_VIATICO + " INTEGER PRIMARY KEY NOT NULL, "
            + VIATICOS_DESC_VIATICO + " TEXT)";

    private final String createSparePartsCatalog = "CREATE TABLE " + SPAREPARTS_DB_NAME + "(" + SPAREPARTS_ID_SPAREPART + " INTEGER PRIMARY KEY NOT NULL, "
            + SPAREPARTS_DESC_SPAREPART + " TEXT)";

    private final String createInsumosCatalog = "CREATE TABLE " + INSUMOS_DB_NAME + "(" + INSUMOS_ID_INSUMO + " INTEGER PRIMARY KEY NOT NULL, "
            + CLIENTES_ID_CLIENTE + " INTEGER, "
            + INSUMOS_ID_TIPO_INSUMO + " INTEGER, "
            + INSUMOS_DESC_INSUMO + " TEXT)";

    private final String createAlmacenesCatalog = "CREATE TABLE " + ALMACENES_DB_NAME + "(" + ALMACENES_ID_ALMACEN + " INTEGER PRIMARY KEY NOT NULL, "
            + ALMACENES_DESC_ALMACEN + " TEXT)";

    private final String createMarcasCatalog = "CREATE TABLE " + MARCAS_DB_NAME + "(" + MARCAS_ID_MARCA + " INTEGER PRIMARY KEY NOT NULL, "
            + PRODUCTO_ID_PRODUCTO + " INTEGER, " + MARCAS_DESC_MARCA + " TEXT)";

    private final String createModelosCatalog = "CREATE TABLE " + MODELOS_DB_NAME + "(" + MODELOS_ID_MODELO + " INTEGER NOT NULL, "
            + MARCAS_ID_MARCA + " INTEGER, "
            + MODELOS_ID_GPRS + " INTEGER, "
            + MODELOS_DESC_MODELO + " TEXT, "
            + MODELOS_SKU + " TEXT)";

    private final String createMSparePartsCatalog = "CREATE TABLE " + MSPAREPARTS_DB_NAME + "(" + MSPAREPARTS_ID_MSPAREPART + " INTEGER PRIMARY KEY NOT NULL, "
            + MODELOS_ID_MODELO + " INTEGER, "
            + SPAREPARTS_ID_SPAREPART + " INTEGER)";

    private final String createDireccionesCatalog = "CREATE TABLE " + DIRECCIONES_DB_NAME + "(" + DIRECCIONES_ID_DIRECCION + " INTEGER PRIMARY KEY NOT NULL, "
            + DIRECCIONES_DIRECCION + " TEXT, "
            + DIRECCIONES_COLONIA + " TEXT, "
            + DIRECCIONES_POBLACION + " TEXT, "
            + DIRECCIONES_ESTADO + " TEXT, "
            + DIRECCIONES_IS_DEFAULT + " TEXT)";

    private final String createServiciosCatalog = "CREATE TABLE " + SERVICES_DB_NAME + "(" + SERVICES_ID_SERVICIO + " INTEGER PRIMARY KEY NOT NULL, "
            + CLIENTES_ID_CLIENTE + " INTEGER, "
            + SERVICES_ID_TIPO_SERVICIO + " TEXT, "
            + SERVICES_ID_MONEDA + " TEXT, "
            + SERVICES_ID_TIPO_PRECIO + " TEXT, "
            + SERVICES_IS_INS_REQ + " TEXT, "
            + SERVICES_IS_CAU_SOL_REQ + " TEXT, "
            + SERVICES_IS_CAU_REQ + " TEXT, "
            + SERVICES_IS_SOL_REQ + " TEXT, "
            + SERVICES_IS_TAS_REQ + " TEXT, "
            + SERVICES_IS_OTOR_TAS_REQ + " TEXT, "
            + SERVICES_IS_NO_EQUIPO_REQ + " TEXT, "
            + SERVICES_IS_NO_SERIE_REQ + " TEXT, "
            + SERVICES_IS_NO_INV_REQ + " TEXT, "
            + SERVICES_ID_IS_MODELO_REQ + " TEXT, "
            + SERVICES_IS_FEC_LLEG_REQ + " TEXT, "
            + SERVICES_IS_FEC_LLE_TER_REQ + " TEXT, "
            + SERVICES_IS_FOLIO_SER_REQ + " TEXT, "
            + SERVICES_IS_FEC_INI_ING_REQ + " TEXT, "
            + SERVICES_IS_FEC_FIN_ING_REQ + " TEXT, "
            + SERVICES_IS_OTOR_VOBO_REQ + " TEXT, "
            + SERVICES_IS_OT_VOBO_TER_REQ + " TEXT, "
            + SERVICES_IS_INT_SEN_REQ + " TEXT, "
            + SERVICES_IS_SIM_REEM_REQ + " TEXT, "
            + SERVICES_IS_FOL_SER_REC_REQ + " TEXT, "
            + SERVICES_IS_OT_VOBO_REC_REQ + " TEXT, "
            + SERVICES_IS_FALLA_ENC_REQ + " TEXT, "
            + SERVICES_IS_OT_VOBO_CLI_REQ + " TEXT, "
            + SERVICES_IS_MOT_COBRO_REQ + " TEXT, "
            + SERVICES_IS_SOPOR_CLI_REQ + " TEXT, "
            + SERVICES_IS_OT_SOP_CLI_REQ + " TEXT, "
            + SERVICES_IS_BOLETIN_REQ + " TEXT, "
            + SERVICES_IS_CAD_CIE_ESC_REQ + " TEXT, "
            + SERVICES_IS_DOWNTIME + " TEXT, "
            + SERVICES_IS_CIERRE_PDA + " TEXT, "
            + SERVICES_IS_APLICACION_REQ + " TEXT, "
            + SERVICES_IS_VERSION_REQ + " TEXT, "
            + SERVICES_IS_CAJA_REQ + " TEXT, "
            + SERVICES_DESC_SERVICIO + " TEXT, "
            // ADD NEW COLUMNS FIELDS (17-10-2014)
            + SERVICES_ID_AR_REOPEN + " TEXT, "
            + SERVICES_ID_AR_NEED_FILE + " TEXT, "
            + SERVICES_ID_AR_NO_CHECKUP + " TEXT, "
            + SERVICES_IS_KIT_REQ + " TEXT, "
            + SERVICES_KIT_SUPPLY + " TEXT,"
            //ADD NEW COLUMNS FIELDS ( 03/08/2017)
            + SERVICES_NEED_SHEET + " TEXT,"
            //30/01/2018
            + SERVICES_CALIDAD_BILLETE + " TEXT,"
            + SERVICES_CONDICION_SITE + " TEXT)";

    private final String createUnidadesCatalog = "CREATE TABLE " + UNIDADES_DB_NAME + "(" + UNIDADES_ID_UNIDAD + " INTEGER, "
            + UNIDADES_ID_STATUS_UNIDAD + " TEXT)";

    private final String createUnidadCatalog = "CREATE TABLE " + UNIDAD_DB_NAME + "(" + UNIDADES_ID_UNIDAD + " INTEGER PRIMARY KEY NOT NULL, "
            + UNIDAD_ID_SOL_REC + " INTEGER, "
            + PRODUCTO_ID_PRODUCTO + " INTEGER, "
            + UNIDAD_IS_NUEVA + " TEXT, "
            + UNIDAD_IS_DANIADA + " TEXT, "
            + UNIDAD_IS_RETIRO + " TEXT, "
            + CLIENTES_ID_CLIENTE + " INTEGER, "
            + UNIDAD_ID_TIPO_PRODUCTO + " INTEGER, "
            + UNIDADES_ID_STATUS_UNIDAD + " INTEGER, "
            + UNIDAD_ID_USUARIO_ALTA + " INTEGER, "
            + UNIDAD_DESC_CLIENTE + " TEXT, "
            + UNIDAD_DESC_MARCA + " TEXT, "
            + UNIDAD_DESC_MODELO + " TEXT, "
            + UNIDAD_NO_SERIE + " TEXT, "
            + UNIDAD_NO_INVENTARIO + " TEXT, "
            + UNIDAD_NO_IMEI + " TEXT, "
            + UNIDAD_NO_SIM + " TEXT, "
            + UNIDAD_NO_EQUIPO + " TEXT, "
            + UNIDAD_DESC_LLAVE + " TEXT, "
            + UNIDAD_DESC_SOFTWARE + " TEXT, "
            + UNIDAD_POSICION_INVENTARIO + " TEXT, "
            + UNIDAD_DESC_STATUS_UNIDAD + " TEXT, "
            + UNIDAD_STATUS + " TEXT, "
            + UNIDAD_FEC_ALTA + " TEXT, "
            + UNIDAD_DESC_ACCESORIO + " TEXT, "
            + UNIDAD_DESCRIPCION_ACC + " TEXT, "
            + UNIDAD_STATUS_ACCESORIO + " TEXT, "
            + UNIDAD_CONN + " TEXT)";

    private final String createCauRecCatalog = "CREATE TABLE " + CAUREC_DB_NAME + "(" + CAUREC_ID_CAUREC + " INTEGER PRIMARY KEY NOT NULL, "
            + CLIENTES_ID_CLIENTE + " INTEGER, "
            + CAUREC_DESC_CAUREC + " TEXT)";

    private final String createEspCauRecCatalog = "CREATE TABLE " + ESPCAUREC_DB_NAME + "(" + ESPCAUREC_ID_ESPCAUREC + " INTEGER PRIMARY KEY NOT NULL, "
            + ESPCAUREC_ID_CAURECPARENT + " INTEGER, "
            + ESPCAUREC_DESC_ESPCAUREC + " TEXT)";

    private final String createCausasCatalog = "CREATE TABLE " + CAUSAS_DB_NAME + "(" + CAUSAS_ID_CAUSA + " INTEGER PRIMARY KEY NOT NULL, "
            + CAUSAS_ID_CLIENTE + " INTEGER, "
            + CAUSAS_CLAVE + " TEXT, "
            + CAUSAS_DESC_CAUSA + " TEXT ) ";

    private final String createSolucionesCatalog = "CREATE TABLE " + SOLUCIONES_DB_NAME + "(" + SOLUCIONES_ID_SOLUCION + " INTEGER PRIMARY KEY NOT NULL, "
            + SOLUCIONES_ID_CLIENTE + " INTEGER, "
            + SOLUCIONES_CLAVE + " TEXT, "
            + SOLUCIONES_IS_EXITO + " TEXT, "
            + SOLUCIONES_DESC_SOLUCION + " TEXT ) ";

    private final String createGruposCatalog = "CREATE TABLE " + GRUPOS_DB_NAME + "(" + GRUPOS_ID_GRUPO + " INTEGER PRIMARY KEY NOT NULL, "
            + GRUPOS_DESC_GRUPO + " TEXT ) ";

    private final String createGruposCliCatalog = "CREATE TABLE " + GRUPOSCLI_DB_NAME + "(" + GRUPOSCLI_ID_GRUPOCLI + " INTEGER PRIMARY KEY NOT NULL, "
            + GRUPOS_ID_GRUPO + " INTEGER, "
            + CLIENTES_ID_CLIENTE + " INTEGER ) ";

    private final String createCodigos0 = "CREATE TABLE " + CODIGOS0_DB_NAME + "(" + CODIGOS0_ID_CODIGO + " INTEGER PRIMARY KEY NOT NULL, "
            + CODIGOS0_CLAVE_CODIGO + " TEXT, "
            + CLIENTES_ID_CLIENTE + " INTEGER, "
            + CODIGOS0_REPORTA_INSTALACION + " INTEGER, "
            + CODIGOS0_DESC_CODIGO + " TEXT)";

    private final String createCodigos1 = "CREATE TABLE " + CODIGOS1_DB_NAME + "(" + CODIGOS1_ID_CODIGO + " INTEGER PRIMARY KEY NOT NULL, "
            + CODIGOS1_CLAVE_CODIGO + " TEXT, "
            + CLIENTES_ID_CLIENTE + " INTEGER, "
            + CODIGOS1_ID_PARENT_0 + " INTEGER, "
            + CODIGOS1_DESC_CODIGO + " TEXT)";

    private final String createCodigos2 = "CREATE TABLE " + CODIGOS2_DB_NAME + "(" + CODIGOS2_ID_CODIGO + " INTEGER PRIMARY KEY NOT NULL, "
            + CODIGOS2_CLAVE_CODIGO + " TEXT, "
            + CLIENTES_ID_CLIENTE + " INTEGER, "
            + CODIGOS2_ID_PARENT_1 + " INTEGER, "
            + CODIGOS2_DESC_CODIGO + " TEXT)";

    private final String createServCauCatalog = "CREATE TABLE " + SERVCAU_DB_NAME + "(" + SERVCAU_ID_SERVCAU + " INTEGER PRIMARY KEY NOT NULL, "
            + SERVICES_ID_SERVICIO + " INTEGER, "
            + CAUSAS_ID_CAUSA + " INTEGER ) ";

    private final String createServSolCatalog = "CREATE TABLE " + SERVSOL_DB_NAME + "(" + SERVSOL_ID_SERVSOL + " INTEGER PRIMARY KEY NOT NULL, "
            + SERVICES_ID_SERVICIO + " INTEGER, "
            + SOLUCIONES_ID_SOLUCION + " INTEGER ) ";

    private final String createCliModCatalog = "CREATE TABLE " + CLIMOD_DB_NAME + "(" + CLIMOD_ID_CLIMOD + " INTEGER PRIMARY KEY NOT NULL, "
            + CLIENTES_ID_CLIENTE + " INTEGER, "
            + MODELOS_ID_MODELO + " INTEGER ) ";

    private final String createTipoFallaCatalog = "CREATE TABLE " + TIPOFALLA_DB_NAME + "(" + TIPOFALLA_ID_TIPOFALLA + " INTEGER NOT NULL, "
            + SERVICES_ID_SERVICIO + " INTEGER, "
            + CLIENTES_ID_CLIENTE + " INTEGER, "
            + TIPOFALLA_DESC_TIPOFALLA + " TEXT )";

    private final String createEspTiFaCatalog = "CREATE TABLE " + ESPTIFA_DB_NAME + "(" + ESPTIFA_ID_ESPTIFA + " INTEGER PRIMARY KEY NOT NULL, "
            + ESPTIFA_ID_FALLA_PARENT + " INTEGER, "
            + ESPTIFA_DESC_ESPTIFA + " TEXT )";

    private final String createTempUnidad = "CREATE TABLE " + LISTAUNIDADES_DB_NAME + "(" + UNIDADES_ID_UNIDAD + " INTEGER, "
            + LISTAUNIDADES_DESC_UNIDAD + " TEXT)";

    private final String createIngenieros = "CREATE TABLE " + INGENIEROS_DB_NAME + "(" + INGENIEROS_ID_INGENIERO + " INTEGER PRIMARY KEY NOT NULL, "
            + INGENIEROS_NOMBRECOMPLETO + " TEXT)";

    private final String createSKUCatalog = "CREATE TABLE " + SKU_DB_NAME + "(" + SKU_ID_SKU + " TEXT NOT NULL, "
            + MODELOS_ID_MODELO + " TEXT, "
            + MARCAS_ID_MARCA + " TEXT, "
            + MARCAS_DESC_MARCA + " TEXT, "
            + SKU_DESC + " TEXT, "
            + MODELOS_DESC_MODELO + " TEXT)";

    private final String createDocumentsCatalog = "CREATE TABLE " + DOCUMENTS_DB_NAME + "(" + REQUESTS_ID_REQUEST + " TEXT PRIMARY KEY NOT NULL, "
            + DOCUMENTS_FECHA + " TEXT)";

    //Para cuando se llena por primera vez el catálogo
    private final String fillNuevasCatalog = "INSERT INTO catalogs (id_catalog, desc, number, md5) values('" + Constants.DATABASE_NUEVAS + "', 'n', '0', '');";
    private final String fillAbiertasCatalog = "INSERT INTO catalogs (id_catalog, desc, number, md5) values('" + Constants.DATABASE_ABIERTAS + "', 'a', '0', '');";
    private final String fillCerradasCatalog = "INSERT INTO catalogs (id_catalog, desc, number, md5) values('" + Constants.DATABASE_CERRADAS + "', 'c', '0', '');";
    private final String fillPendientesCatalog = "INSERT INTO catalogs (id_catalog, desc, number, md5) values('" + Constants.DATABASE_PENDIENTES + "', 'p', '0', '');";
    private final String fillUnidadesCatalog = "INSERT INTO catalogs (id_catalog, desc, number, md5) values('" + Constants.DATABASE_UNIDADES + "', 'u', '0', '');";
    private final String fillDireccionesCatalog = "INSERT INTO catalogs (id_catalog, desc, number, md5) values('" + Constants.DATABASE_DIRECCIONES + "', 'dir', '0', '');";
    private final String fillEnviosPendCatalog = "INSERT INTO catalogs (id_catalog, desc, number, md5) values('" + Constants.DATABASE_ENVIOSPEND + "', 'ep', '0', '');";
    private final String fillRecepcionesCatalog = "INSERT INTO catalogs (id_catalog, desc, number, md5) values('" + Constants.DATABASE_RECEPCIONES + "', 'er', '0', '');";

    //MD5 Table
    private final String fillStatusExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_STATUS    	+ ", '" + STATUS_DB_NAME 		+	"', '0');";
    private final String fillProductosExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_PRODUCTOS 	+ ", '" + PRODUCTO_DB_NAME 		+ 	"', '0');";
    private final String fillCambiosExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_CAMBIOS   	+ ", '" + CAMBIO_DB_NAME 		+ 	"', '0');";
    private final String fillViaticosExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_VIATICOS  	+ ", '" + VIATICOS_DB_NAME 		+ 	"', '0');";   
    private final String fillSparePartsExtra	= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_SPARE_PARTS  + ", '" + SPAREPARTS_DB_NAME 	+ 	"', '0');";    		
    private final String fillInsumosExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_INSUMOS  	+ ", '" + INSUMOS_DB_NAME 		+ 	"', '0');";    		
    private final String fillAlmacenesExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_ALMACENES  	+ ", '" + ALMACENES_DB_NAME 	+ 	"', '0');";    		
    private final String fillMarcasExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_MARCAS  		+ ", '" + MARCAS_DB_NAME 		+ 	"', '0');";    		
    private final String fillMSparePartsExtra	= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_MSPARE_PARTS + ", '" + MSPAREPARTS_DB_NAME 	+ 	"', '0');";
    private final String fillModelosExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_MODELOS    	+ ", '" + MODELOS_DB_NAME 		+ 	"', '0');";
    private final String fillServiciosExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_SERVICIOS    + ", '" + SERVICES_DB_NAME + 	"', '0');";
    private final String fillCausasExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_CAUSAS    		+ ", '" + CAUSAS_DB_NAME 			+ 	"', '0');"; 
    private final String fillCauRecExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_CAUSASRECHAZO    + ", '" + CAUSAS_DB_NAME 			+ 	"', '0');"; 
    private final String fillEspCauRecExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_ESPECCAUREC    	+ ", '" + ESPCAUREC_DB_NAME 		+ 	"', '0');"; 
    private final String fillSolucionesExtra	= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_SOLUCIONES    	+ ", '" + SOLUCIONES_DB_NAME 		+ 	"', '0');"; 
    private final String fillGruposExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_GRUPOS    		+ ", '" + GRUPOS_DB_NAME 			+ 	"', '0');"; 
    private final String fillGruposCliExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_GRUPOSCLIENTES   + ", '" + GRUPOSCLI_DB_NAME			+ 	"', '0');"; 
    private final String fillCodigos0Extra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_CODIGOS0    		+ ", '" + CODIGOS0_DB_NAME 			+ 	"', '0');"; 
    private final String fillCodigos1Extra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_CODIGOS1    		+ ", '" + CODIGOS1_DB_NAME 			+ 	"', '0');"; 
    private final String fillCodigos2Extra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_CODIGOS2    		+ ", '" + CODIGOS2_DB_NAME 			+ 	"', '0');"; 
    private final String fillClientesModExtra	= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_CLIENTESMOD    	+ ", '" + CLIENTES_DB_NAME 			+ 	"', '0');"; 
    private final String fillServSolExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_SERVSOL    		+ ", '" + SERVSOL_DB_NAME 			+ 	"', '0');"; 
    private final String fillTipoFallaExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_TIPOFALLA    	+ ", '" + TIPOFALLA_DB_NAME 		+ 	"', '0');"; 
    private final String fillEspTipoFallaExtra	= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_ESPTIPOFALLA    	+ ", '" + ESPTIFA_DB_NAME 			+ 	"', '0');"; 
    private final String fillServCauExtra		= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_SERVCAU    		+ ", '" + SERVCAU_DB_NAME 			+ 	"', '0');"; 
    private final String fillIngenierosExtra	= "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", " + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_INGENIEROS    	+ ", '" + INGENIEROS_DB_NAME 		+ 	"', '0');";

    public SQLiteHelper(Context context,
                               CursorFactory factory)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public SQLiteDatabase getWritableDB()
    {
    	synchronized(SQLiteDatabase.dbLock)
        {
    		return new SQLiteDatabase(getWritableDatabase());
    	}
    }
    
    public SQLiteDatabase getReadableDB()
    {
    	synchronized(SQLiteDatabase.dbLock)
        {
    		return new SQLiteDatabase(getReadableDatabase());
    	}
    }
    
	@Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db)
    {
		//Crea las tablas
        String createClients = "CREATE TABLE " + CLIENTS_DB_NAME + "(" + CLIENTS_ID_CLIENT + " INTEGER, "
                + CLIENTS_DESC_CLIENT + " TEXT, "+CLIENTS_MOBILE+" INTEGER)";

        String createRequests = "CREATE TABLE requests(id_request TEXT PRIMARY KEY NOT NULL, "
            + "id_catalog INTEGER, id_status TEXT, is_key_account TEXT, horas_garantia TEXT, "
            + "horas_atencion TEXT, fec_alta TEXT, fec_atencion TEXT, fec_garantia TEXT, fec_cierre TEXT, "
            + "id_producto TEXT, id_unidad_atendida TEXT, id_modelo TEXT, id_cliente TEXT, id_servicio "
            + "TEXT, id_falla TEXT, id_negocio TEXT, no_ar TEXT, desc_cliente TEXT, no_afiliacion TEXT, "
            + "desc_servicio TEXT, sintoma, concepto TEXT, desc_corta TEXT, bitacora TEXT, "
            + "notas_remedy TEXT, desc_equipo TEXT, equipo TEXT, no_serie TEXT, direccion TEXT, "
            + "colonia TEXT, poblacion TEXT, estado TEXT, cp TEXT, desc_negocio TEXT, telefono TEXT, "
            + "caja TEXT, v_prefacturacion INTEGER, v_comentario TEXT, no_sim TEXT, clave_rechazo TEXT, "
            + "desc_software TEXT, desc_connectivity TEXT, "
            + "FOREIGN KEY (id_catalog) REFERENCES catalogs (id_catalog));";

        String createInvSupplies = "CREATE TABLE " + INV_SUPPLIES_DB_NAME + "(" + INV_SUPPLIES_ID_SUPPLY + " INTEGER PRIMARY KEY NOT NULL, "
                + INV_SUPPLIES_DESC_CLIENT + " TEXT, " + INV_SUPPLIES_DESC + " TEXT, "
                + INV_SUPPLIES_TOTAL + " INTEGER)";

        String createRecepcionesCatalog = "CREATE TABLE " + RECEPTIONS_DB_NAME + "("
                + RECEPTIONS_ID_SHIPMENT + " INTEGER PRIMARY KEY NOT NULL, "
                + RECEPTIONS_TYPE + " TEXT, "
                + RECEPTIONS_COUNT + " INTEGER)";
        //SE QUITO LA PRIMARY KEY DE LOS ENVIOS EN PROCESO
        String createProcessShipmentCatalog = "CREATE TABLE " + PROCESS_SHIPMENT_DB_NAME + "("
                + PROCESS_SHIPMENT_ID + " INTEGER , "
                + PROCESS_SHIPMENT_TYPE + " TEXT, "
                + PROCESS_SHIPMENT_COUNT + " INTEGER)";

        String createConnectivity = "CREATE TABLE " + CONNECTIVITY_DB_NAME + "("
            + CONNECTIVITY_ID + " INTEGER PRIMARY KEY NOT NULL, "
            + CONNECTIVITY_ID_CLIENT + " INTEGER, "
            + CONNECTIVITY_DESC + " TEXT, "
            + CONNECTIVITY_IS_GPRS + " INTEGER)";

        String createCausaRetiro = "CREATE TABLE " + CAUSA_RETIRO_DB_NAME + "("
                + CAUSA_RETIRO_ID + " INTEGER PRIMARY KEY NOT NULL, "
                + CAUSA_RETIRO_ID_CLIENTE + " INTEGER, "
                + CAUSA_RETIRO_DESC + " TEXT, "
                + CAUSA_RETIRO_STATUS + " TEXT)";

        /*
            Inicia cambio 16/06/2017
         */
        String createEtiqietas = "CREATE TABLE " + ETIQUETAS_BD_NAME + "("
                + ETIQUETAS_ID + " INTEGER NOT NULL, "
                + ETIQUETAS_ID_CLIENTE + " INTEGER, "
                + ETIQUETAS_DESC + " TEXT)";
        /*FIN*/

        String createSoftware = "CREATE TABLE " + SOFTWARE_DB_NAME + "("
                + SOFTWARE_ID + " INTEGER PRIMARY KEY NOT NULL, "
                + SOFTWARE_ID_CLIENT + " INTEGER, "
                + SOFTWARE_DESC + " TEXT)";

        String createFailsFound = "CREATE TABLE " + FAILS_FOUND_DB_NAME + "("
                + FAILS_FOUND_ID + " INTEGER NOT NULL, "
                + FAILS_FOUND_DESC + " TEXT, " + FAILS_FOUND_ID_FATHER + " INTEGER, "
                + FAILS_FOUND_ID_CLIENT + " INTEGER)";

        String fillInvSuppliesCatalog = "INSERT INTO catalogs (id_catalog, desc, number, md5) values('"
                + Constants.DATABASE_INV_SUPPLIES + "', 'inv_supplies', '0', '');";

        String fillExtraClient = "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", "
                + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_CLIENTS + ", '"
                + CLIENTS_DB_NAME + "', '0');";

        String fillExtraConnectivity = "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", "
                + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_CONNECTIVITY + ", '"
                + CONNECTIVITY_DB_NAME + "', '0');";

        String fillExtraSoftware = "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", "
                + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_SOFTWARE + ", '"
                + SOFTWARE_DB_NAME + "', '0');";

        String fillExtraFailsFounds = "INSERT INTO " + EXTRA_DB_NAME + " (" + EXTRA_ID_EXTRA + ", "
                + EXTRA_DESC + ", " + EXTRA_DATE + ") values(" + Constants.CATALOG_FAILS_FOUNDS + ", '"
                + FAILS_FOUND_DB_NAME + 	"', '0');";

        db.execSQL(createClients);
        db.execSQL(createInvSupplies);
        db.execSQL(createConnectivity);
        db.execSQL(createSoftware);
        db.execSQL(createCausaRetiro);
        db.execSQL(createEtiqietas);
    	db.execSQL(createCatalogs);
    	db.execSQL(createRequests);
    	db.execSQL(createOtherCatalogs);
        db.execSQL(createFailsFound);

    	//Crea los catálogos
    	db.execSQL(createStatusCatalog);
    	db.execSQL(createCambioCatalog);
    	db.execSQL(createProductosCatalog);
    	
    	db.execSQL(createViaticosCatalog);
    	db.execSQL(createSparePartsCatalog);
    	db.execSQL(createInsumosCatalog);
    	db.execSQL(createAlmacenesCatalog);
    	db.execSQL(createMarcasCatalog);
    	db.execSQL(createModelosCatalog);
    	db.execSQL(createMSparePartsCatalog);
    	db.execSQL(createDireccionesCatalog);
    	db.execSQL(createServiciosCatalog);
    	db.execSQL(createUnidadesCatalog);
    	db.execSQL(createProcessShipmentCatalog);
    	db.execSQL(createRecepcionesCatalog);
    	db.execSQL(createUnidadCatalog);
    	db.execSQL(createCausasCatalog);
    	db.execSQL(createCauRecCatalog);
    	db.execSQL(createEspCauRecCatalog);
    	db.execSQL(createSolucionesCatalog);
    	db.execSQL(createGruposCatalog);
    	db.execSQL(createGruposCliCatalog);
    	db.execSQL(createCodigos0);
    	db.execSQL(createCodigos1);
    	db.execSQL(createCodigos2);
    	db.execSQL(createServCauCatalog);
    	db.execSQL(createServSolCatalog);
    	db.execSQL(createCliModCatalog);
    	db.execSQL(createTipoFallaCatalog);
    	db.execSQL(createEspTiFaCatalog);
    	db.execSQL(createIngenieros);
    	db.execSQL(createSKUCatalog);
    	/*JDOS Se agrega Creacion de catalogo 30/01/2018*/
    	db.execSQL(createCalidadBillete);
    	db.execSQL(createClienteCalidadBillete);
        db.execSQL(createCondicionesSite);
        db.execSQL(createClienteCondicionesSite);

    	//Catálogos sin MD5
    	db.execSQL(createClientesCatalog);
    	db.execSQL(createListaViaticosCatalog);
    	db.execSQL(createTempUnidad);
    	db.execSQL(createDocumentsCatalog);
    	
    	//Llena los catálogos con valores iniciales
    	db.execSQL(fillNuevasCatalog);
    	db.execSQL(fillAbiertasCatalog);
    	db.execSQL(fillCerradasCatalog);
    	db.execSQL(fillPendientesCatalog);
    	db.execSQL(fillUnidadesCatalog);
        db.execSQL(fillInvSuppliesCatalog);
    	db.execSQL(fillDireccionesCatalog);
    	db.execSQL(fillEnviosPendCatalog);
    	db.execSQL(fillRecepcionesCatalog);

        // MD5 Rows
    	db.execSQL(fillStatusExtra);
    	db.execSQL(fillProductosExtra);
    	db.execSQL(fillCambiosExtra);
    	db.execSQL(fillViaticosExtra);
    	db.execSQL(fillSparePartsExtra);
    	db.execSQL(fillInsumosExtra);
    	db.execSQL(fillAlmacenesExtra);
    	db.execSQL(fillMarcasExtra);
    	db.execSQL(fillMSparePartsExtra);
    	db.execSQL(fillModelosExtra);
    	db.execSQL(fillServiciosExtra);
    	db.execSQL(fillCausasExtra);
    	db.execSQL(fillCauRecExtra);
    	db.execSQL(fillEspCauRecExtra);
    	db.execSQL(fillSolucionesExtra);
    	db.execSQL(fillGruposExtra);
    	db.execSQL(fillGruposCliExtra);
    	db.execSQL(fillCodigos0Extra);
    	db.execSQL(fillCodigos1Extra);
    	db.execSQL(fillCodigos2Extra);
    	db.execSQL(fillClientesModExtra);
    	db.execSQL(fillServSolExtra);
    	db.execSQL(fillTipoFallaExtra);
    	db.execSQL(fillEspTipoFallaExtra);
    	db.execSQL(fillServCauExtra);
    	db.execSQL(fillIngenierosExtra);
        db.execSQL(fillExtraClient);
        db.execSQL(fillExtraConnectivity);
        db.execSQL(fillExtraSoftware);
        db.execSQL(fillExtraFailsFounds);
    }
	
    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int versionAnterior, int versionNueva)
    {
    	db.execSQL("DROP TABLE " + DOCUMENTS_DB_NAME);
        Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + DOCUMENTS_DB_NAME + "'", null);

        try
        {
        	if (cursor != null )
            {
        		if  (!cursor.moveToFirst())
                {
                    db.execSQL(createDocumentsCatalog);
        		}
        	}
        }
        catch(Exception ex)
        {
            Log.d("Microformas", "UpgradeDB= " + ex.getMessage());
        }
        finally
        {
            if(cursor != null)
            {
                cursor.close();
            }
        }
    }

    public Cursor getDisplayStatus(String idCliente, String statusInicial, String idProducto, SQLiteDatabase db)
	{   // Despliega los status a los que puede pasarse una solicitud
        String[] args = new String[]{idCliente, statusInicial, idProducto};
        return db.rawQuery("SELECT status_fin FROM cambio_status"
            + " WHERE id_cliente = ? AND status_ini = ?"
            + " AND status_fin IN (SELECT id_status FROM productos_status WHERE id_producto = ?)", args);
    }

    public long setRequests(RequestDetailBean requestDetailBean, SQLiteDatabase db)
	{	// Sección para insertar valores en BD
        ContentValues initialValues = new ContentValues();
        initialValues.put(REQUESTS_ID_REQUEST, requestDetailBean.getIdAr());
        initialValues.put(CATALOGS_ID_CATALOG, requestDetailBean.getIdCatalogo());
        initialValues.put(STATUS_ID_STATUS, requestDetailBean.getIdStatusAr());
    	initialValues.put(REQUESTS_IS_KEY_ACCOUNT, requestDetailBean.getIsKeyAccount());
    	initialValues.put(REQUESTS_HORAS_GARANTIA, requestDetailBean.getHorasGarantia());
    	initialValues.put(REQUESTS_HORAS_ATENCION, requestDetailBean.getHorasAtencion());
    	initialValues.put(REQUESTS_FEC_ALTA, requestDetailBean.getFecAlta());
    	initialValues.put(REQUESTS_FEC_ATENCION, requestDetailBean.getFecAtencion());
    	initialValues.put(REQUESTS_FEC_GARANTIA, requestDetailBean.getFecGarantia());
    	initialValues.put(REQUESTS_FEC_CIERRE, requestDetailBean.getFecCierre());
    	initialValues.put(REQUESTS_ID_PRODUCTO, requestDetailBean.getIdProducto());
    	initialValues.put(REQUESTS_ID_UNIDAD_ATENDIDA, requestDetailBean.getIdUnidadAtendida());
    	initialValues.put(REQUESTS_ID_MODELO, requestDetailBean.getIdModelo());
    	initialValues.put(CLIENTES_ID_CLIENTE, requestDetailBean.getIdCliente());
    	initialValues.put(REQUESTS_ID_SERVICIO, requestDetailBean.getIdServicio());
    	initialValues.put(REQUESTS_ID_NEGOCIO, requestDetailBean.getIdNegocio());
    	initialValues.put(REQUESTS_NO_AR, requestDetailBean.getNoAr());
    	initialValues.put(REQUESTS_DESC_CLIENTE, requestDetailBean.getDescCliente());
    	initialValues.put(REQUESTS_NO_AFILIACION, requestDetailBean.getNoAfiliacion());
    	initialValues.put(REQUESTS_DESC_SERVICIO, requestDetailBean.getDescServicio());
    	initialValues.put(REQUESTS_SINTOMA, requestDetailBean.getSintoma());
    	initialValues.put(REQUESTS_CONCEPTO, requestDetailBean.getConcepto());
    	initialValues.put(REQUESTS_DESC_CORTA, requestDetailBean.getDescCorta());
    	initialValues.put(REQUESTS_BITACORA, requestDetailBean.getBitacora());
    	initialValues.put(REQUESTS_NOTAS_REMEDY, requestDetailBean.getNotasRemedy());
    	initialValues.put(REQUESTS_DESC_EQUIPO, requestDetailBean.getDescEquipo());
    	initialValues.put(REQUESTS_EQUIPO, requestDetailBean.getEquipo());
    	initialValues.put(REQUESTS_NO_SERIE, requestDetailBean.getNoSerie());
    	initialValues.put(REQUESTS_DIRECCION, requestDetailBean.getDireccion());
    	initialValues.put(REQUESTS_COLONIA, requestDetailBean.getColonia());
    	initialValues.put(REQUESTS_POBLACION, requestDetailBean.getPoblacion());
    	initialValues.put(REQUESTS_ESTADO, requestDetailBean.getEstado());
    	initialValues.put(REQUESTS_CP, requestDetailBean.getCp());
    	initialValues.put(REQUESTS_DESC_NEGOCIO, requestDetailBean.getDescNegocio());
    	initialValues.put(REQUESTS_TELEFONO, requestDetailBean.getTelefono());
    	initialValues.put(REQUESTS_CAJA, requestDetailBean.getCaja());
    	initialValues.put(REQUESTS_PREFACTURACION, requestDetailBean.getvPreFacturacion());
    	initialValues.put(REQUESTS_COMENTARIO, requestDetailBean.getvComentario());
    	initialValues.put(REQUESTS_NO_SIM, requestDetailBean.getSim());
    	initialValues.put(REQUESTS_CLAVE_RECHAZO, requestDetailBean.getClaveRechazo());
        initialValues.put(REQUESTS_ID_FALLA, requestDetailBean.getIdFalla());
        initialValues.put(REQUESTS_DESC_SOFTWARE, requestDetailBean.getSoftware());
        initialValues.put(REQUESTS_DESC_CONN, requestDetailBean.getConnectivity());
        initialValues.put(REQUESTS_ID_CLIENTE,requestDetailBean.getIdCliente());

    	//Adquiere la cantidad de registros que tiene un módulo
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + REQUESTS_DB_NAME + " WHERE "
            + REQUESTS_ID_REQUEST + " = " + requestDetailBean.getIdAr(), null);

        int count = 0;
        try
        {
        	if (cursor != null )
            {
        		if  (cursor.moveToFirst())
                {
        			do
                    {
        		        count = Integer.valueOf(cursor.getString(0));
        			} while (cursor.moveToNext());
        		}
        	}

            if(count > 0)
            {
                db.execSQL("DELETE FROM " + REQUESTS_DB_NAME
                        + " WHERE " + REQUESTS_ID_REQUEST + " = " + requestDetailBean.getIdAr());
            }
        }
        catch(Exception ex)
        {
            Log.d("Microformas", "setRequests= " + ex.getMessage());
        }

        if(cursor != null)
        {
            cursor.close();
        }

    	return db.insert(REQUESTS_DB_NAME, null, initialValues);
    }

    public long setClient(ClientBean clientBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(CLIENTS_ID_CLIENT, clientBean.getId());
        initialValues.put(CLIENTS_DESC_CLIENT, clientBean.getDescription());
        initialValues.put(CLIENTS_MOBILE, clientBean.getActive_mobile_notification());
        return db.insert(CLIENTS_DB_NAME, null, initialValues);
    }

    public long setConnectivity(ConnectivityBean connectivityBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(CONNECTIVITY_ID, connectivityBean.getId());
        initialValues.put(CONNECTIVITY_ID_CLIENT, connectivityBean.getIdClient());
        initialValues.put(CONNECTIVITY_DESC, connectivityBean.getDescription());
        initialValues.put(CONNECTIVITY_IS_GPRS, connectivityBean.isGPRS());
        return db.insert(CONNECTIVITY_DB_NAME, null, initialValues);
    }

    /*
    * Catalogo de causas retiro 22/03/2017 JDOS
    * */

    public long setCausaRetiro(CausaRetiroBean causaRetiroBean, SQLiteDatabase db){

        ContentValues initialValues = new ContentValues();
        initialValues.put(CAUSA_RETIRO_ID,causaRetiroBean.getId());
        initialValues.put(CAUSA_RETIRO_ID_CLIENTE,causaRetiroBean.getIdcliente());
        initialValues.put(CAUSA_RETIRO_DESC,causaRetiroBean.getDescCausaRetiro());
        initialValues.put(CAUSA_RETIRO_STATUS,causaRetiroBean.getStatus());
        return db.insert(CAUSA_RETIRO_DB_NAME,null,initialValues);

    }

    /* INICIA CAMBIO 16082017 */
    public long setEtiquetas(EtiquetasBean etiquetasBean, SQLiteDatabase db){

        ContentValues iniValues = new ContentValues();
        iniValues.put(ETIQUETAS_ID,etiquetasBean.getId());
        iniValues.put(ETIQUETAS_ID_CLIENTE,etiquetasBean.getIdcliente());
        iniValues.put(ETIQUETAS_DESC,etiquetasBean.getEtiqueta());
        return db.insert(ETIQUETAS_BD_NAME,null,iniValues);
    }
    /*JDOS SE AGREGA INSERT 30/01/2018*/
    public long setCalidadBillete(CalidadBilleteBean billeteBean, SQLiteDatabase db){
        ContentValues initialValues = new ContentValues();
        initialValues.put(CALIDAD_BILLETE_ID,billeteBean.getId());
        initialValues.put(CALIDAD_BILLETE_DESC,billeteBean.getDescCalidadBillete());
        return db.insert(CALIDAD_BILLETE, null,initialValues);
    }

    public long setCondicionSite(CondicionSiteBean siteBean, SQLiteDatabase db){
        ContentValues initialValues = new ContentValues();
        initialValues.put(CONDICIONES_SITE_ID,siteBean.getId());
        initialValues.put(CONDICIONES_SITE_DESC,siteBean.getDescCondicionSite());
        return db.insert(CONDICIONES_SITE, null,initialValues);
    }

    public long setClienteCalidadBillete(ClienteCalidadBilleteBean clienteCalidadBilleteBean, SQLiteDatabase db){
        ContentValues initialValues = new ContentValues();
        initialValues.put(CLIENTE_CALIDAD_BILLETE_ID,clienteCalidadBilleteBean.getIdClienteCalidadBillete());
        initialValues.put(CLIENTE_CALIDAD_BILLETE_ID_CLIENTE,clienteCalidadBilleteBean.getIdCliente());
        initialValues.put(CLIENTE_CALIDAD_BILLETE_ID_CALIDAD,clienteCalidadBilleteBean.getIdCalidadBillete());
        return db.insert(CLIENTE_CALIDAD_BILLETE, null,initialValues);
    }

    public long setClienteCondicionSite(ClienteCondicionSiteBean clienteCondicionSiteBean, SQLiteDatabase db){
        ContentValues initialValues = new ContentValues();
        initialValues.put(CLIENTE_CONDICIONES_SITE_ID,clienteCondicionSiteBean.getIdClienteCondicionSite());
        initialValues.put(CLIENTE_CONDICIONES_SITE_ID_CLIENTE,clienteCondicionSiteBean.getIdCliente());
        initialValues.put(CLIENTE_CONDICIONES_SITE_ID_CONDICION,clienteCondicionSiteBean.getIdCondicionSite());
        return db.insert(CLIENTE_CONDICIONES_SITE, null,initialValues);
    }
    /*FIN*/

    public long setSoftware(SoftwareBean softwareBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(SOFTWARE_ID, softwareBean.getId());
        initialValues.put(SOFTWARE_ID_CLIENT, softwareBean.getIdClient());
        initialValues.put(SOFTWARE_DESC, softwareBean.getDescription());
        return db.insert(SOFTWARE_DB_NAME, null, initialValues);
    }

    public long setStatus(StatusBean statusBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(STATUS_ID_STATUS, statusBean.getId());
        initialValues.put(STATUS_IS_NUEVAS, statusBean.getIsNuevas());
        initialValues.put(STATUS_IS_ABIERTAS, statusBean.getIsAbiertas());
        initialValues.put(STATUS_IS_CERRADAS, statusBean.getIsCerradas());
        initialValues.put(STATUS_IS_PENDIENTES, statusBean.getIsPendientes());
        initialValues.put(STATUS_IS_SOLICITUD_ALMACEN, statusBean.getIsSolicitudAlmacen());
        initialValues.put(STATUS_IS_SOLICITUD_VIATICOS, statusBean.getIsSolicitudViaticos());
        initialValues.put(STATUS_DESC_STATUS, statusBean.getDescStatus());
        initialValues.put(STATUS_ACTIVO, statusBean.getActivo());
        initialValues.put(STATUS_ORDEN, statusBean.getOrden());
        initialValues.put(STATUS_MOBILE, statusBean.getActive_mobile_notification());
    	return db.insert(STATUS_DB_NAME, null, initialValues);
    }
    
    public long setClientesCambios(ClientesCambiosStatusBean clientesCambiosStatusBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(CLIENTES_ID_CLIENTE, clientesCambiosStatusBean.getIdCliente());
    	return db.insert(CLIENTES_DB_NAME, null, initialValues);
    }
    
    public long setListaViaticos(String id, String concepto, String costo, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(VIATICOS_ID_VIATICO, id);
        initialValues.put(LISTAVIATICOS_CONCEPTO, concepto);
        initialValues.put(LISTAVIATICOS_COSTO, costo);
    	return db.insert(LISTAVIATICOS_DB_NAME, null, initialValues);
    }
    
    public long setListaUnidades(String id, String descripcion, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(UNIDADES_ID_UNIDAD, id);
        initialValues.put(LISTAUNIDADES_DESC_UNIDAD, descripcion);
    	return db.insert(LISTAUNIDADES_DB_NAME, null, initialValues);
    }
    
    public long setSKU(SKUBean skuBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(SKU_ID_SKU, skuBean.getSku());
        initialValues.put(MODELOS_ID_MODELO, skuBean.getIdModelo());
        initialValues.put(MARCAS_ID_MARCA, skuBean.getIdMarca());
        initialValues.put(MARCAS_DESC_MARCA, skuBean.getDescMarca());
        initialValues.put(SKU_DESC, skuBean.getDescripcion());
        initialValues.put(MODELOS_DESC_MODELO, skuBean.getDescModelo());
    	return db.insert(SKU_DB_NAME, null, initialValues);
    }
    
    public long setDocumentsCatalog(String idRequest, SQLiteDatabase db)
    {
    	Calendar c = Calendar.getInstance(); 
    	
        ContentValues initialValues = new ContentValues();
        initialValues.put(REQUESTS_ID_REQUEST, idRequest);
        initialValues.put(DOCUMENTS_FECHA, c.get(Calendar.DAY_OF_MONTH) + "/" 
        								 + c.get(Calendar.MONTH)		+ "/"
        								 + c.get(Calendar.YEAR) 		+ " "
        								 + c.get(Calendar.HOUR)			+ ":"
        								 + c.get(Calendar.MINUTE));
        
        Cursor mCount = db.rawQuery("SELECT count(*) FROM " + DOCUMENTS_DB_NAME + " WHERE  " + REQUESTS_ID_REQUEST + " = " + idRequest, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        
        if(count == 0)
        {
        	return db.insert(DOCUMENTS_DB_NAME, null, initialValues);
        }
        else
        {
        	return 15;
        }
    }
     
    public int setExtraCatalogs(NotificationsUpdateBean notificationsUpdateBean, int idCatalog, SQLiteDatabase db)
    {   // Sección para editar valores existentes
    	ContentValues args = new ContentValues();
        switch (idCatalog)
        {
            case Constants.CATALOG_STATUS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getStatusDate());
                break;

            case Constants.CATALOG_PRODUCTOS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getProductosDate());
                break;

            case Constants.CATALOG_CAMBIOS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getCambiosDate());
                break;

            case Constants.CATALOG_VIATICOS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getViaticosDate());
                break;

            case Constants.CATALOG_SPARE_PARTS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getSparePartDate());
                break;

            case Constants.CATALOG_INSUMOS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getInsumosDate());
                break;

            case Constants.CATALOG_ALMACENES:
                args.put(EXTRA_DATE, notificationsUpdateBean.getAlmacenesDate());
                break;

            case Constants.CATALOG_MARCAS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getMarcasDate());
                break;

            case Constants.CATALOG_MSPARE_PARTS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getMSparePartsDate());
                break;

            case Constants.CATALOG_MODELOS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getModelosDate());
                break;

            case Constants.CATALOG_SERVICIOS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getServiciosDate());
                break;

            case Constants.CATALOG_CAUSAS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getCausasDate());
                break;

            case Constants.CATALOG_CAUSASRECHAZO:
                args.put(EXTRA_DATE, notificationsUpdateBean.getCausasRechazoDate());
                break;

            case Constants.CATALOG_ESPECCAUREC:
                args.put(EXTRA_DATE, notificationsUpdateBean.getEspecificaCausasRechazoDate());
                break;

            case Constants.CATALOG_SOLUCIONES:
                args.put(EXTRA_DATE, notificationsUpdateBean.getSolucionesDate());
                break;

            case Constants.CATALOG_GRUPOS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getGruposDate());
                break;

            case Constants.CATALOG_GRUPOSCLIENTES:
                args.put(EXTRA_DATE, notificationsUpdateBean.getGruposClientesDate());
                break;

            case Constants.CATALOG_CODIGOS0:
                args.put(EXTRA_DATE, notificationsUpdateBean.getCodigos0Date());
                break;

            case Constants.CATALOG_CODIGOS1:
                args.put(EXTRA_DATE, notificationsUpdateBean.getCodigos1Date());
                break;

            case Constants.CATALOG_CODIGOS2:
                args.put(EXTRA_DATE, notificationsUpdateBean.getCodigos2Date());
                break;

            case Constants.CATALOG_SERVCAU:
                args.put(EXTRA_DATE, notificationsUpdateBean.getServCauDate());
                break;

            case Constants.CATALOG_SERVSOL:
                args.put(EXTRA_DATE, notificationsUpdateBean.getServSolDate());
                break;

            case Constants.CATALOG_CLIENTESMOD:
                args.put(EXTRA_DATE, notificationsUpdateBean.getClientesModDate());
                break;

            case Constants.CATALOG_TIPOFALLA:
                args.put(EXTRA_DATE, notificationsUpdateBean.getTipoFallaDate());
                break;

            case Constants.CATALOG_ESPTIPOFALLA:
                args.put(EXTRA_DATE, notificationsUpdateBean.getEspTipoFallaDate());
                break;

            case Constants.CATALOG_INGENIEROS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getIngenierosDate());
                break;

            case Constants.CATALOG_CLIENTS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getDateClients());
                break;

            case Constants.CATALOG_CONNECTIVITY:
                args.put(EXTRA_DATE, notificationsUpdateBean.getDateConnectivity());
                break;

            case Constants.CATALOG_SOFTWARE:
                args.put(EXTRA_DATE, notificationsUpdateBean.getDateSoftware());
                break;

            case Constants.CATALOG_CAUSAS_RETIRO:
                args.put(EXTRA_DATE,notificationsUpdateBean.getDateCausaRetiro());
                break;

            case Constants.CATALOG_ETIQUETAS:
                args.put(EXTRA_DATE,notificationsUpdateBean.getDateEtiquetas());
                break;

            case Constants.CATALOG_FAILS_FOUNDS:
                args.put(EXTRA_DATE, notificationsUpdateBean.getFailsFoundDate());
                break;

            case Constants.CATALOG_CALIDAD_BILLETE:
                args.put(EXTRA_DATE,notificationsUpdateBean.getCalidadBilleteDate());
                break;

            case Constants.CATALOG_CONDICION_SITE:
                args.put(EXTRA_DATE,notificationsUpdateBean.getCondicionSiteDate());
                break;

            case Constants.CATALOG_C_CALIDAD_BILLETE:
                args.put(EXTRA_DATE,notificationsUpdateBean.getCcalidadBilleteDate());
                break;

            case Constants.CATALOG_C_CONDICION_SITE:
                args.put(EXTRA_DATE,notificationsUpdateBean.getCcondicionSiteDate());
                break;

        }

    	String[] conditional = new String[] { String.valueOf(idCatalog) };
    	return db.update(EXTRA_DB_NAME, args, EXTRA_ID_EXTRA + "=?", conditional);
    }
    
    public long setCambioStatus(CambiosStatusBean cambioStatusBean, String idCliente, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(CLIENTES_ID_CLIENTE, idCliente);
        initialValues.put(CAMBIO_STATUS_AR_INI,	cambioStatusBean.getIdStatusIni());
        initialValues.put(CAMBIO_STATUS_AR_FIN,	cambioStatusBean.getIdStatusFin());
    	return db.insert(CAMBIO_DB_NAME, null, initialValues);
    }
    
    public long setProductosStatus(ProductosStatusBean productosStatusBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PRODUCTO_ID_PRODUCTO, productosStatusBean.getIdProducto());
        initialValues.put(STATUS_ID_STATUS, productosStatusBean.getIdStatus());
    	return db.insert(PRODUCTO_DB_NAME, null, initialValues);
    }
    
    public int setCatalogs(NotificationsUpdateBean notificationsUpdateBean, int type, SQLiteDatabase db)
    {
    	ContentValues args = new ContentValues();
        switch (type)
        {
            case Constants.DATABASE_NUEVAS:
                args.put(CATALOGS_NUMBER, notificationsUpdateBean.getNuevasNumber());
                args.put(CATALOGS_MD5, notificationsUpdateBean.getNuevasMD5());
                break;

            case Constants.DATABASE_ABIERTAS:
                args.put(CATALOGS_NUMBER, notificationsUpdateBean.getAbiertasNumber());
                args.put(CATALOGS_MD5, notificationsUpdateBean.getAbiertasMD5());
                break;

            case Constants.DATABASE_CERRADAS:
                args.put(CATALOGS_NUMBER, notificationsUpdateBean.getCerradasNumber());
                args.put(CATALOGS_MD5, notificationsUpdateBean.getCerradasMD5());
                break;

            case Constants.DATABASE_PENDIENTES:
                args.put(CATALOGS_NUMBER, notificationsUpdateBean.getPendientesNumber());
                args.put(CATALOGS_MD5, notificationsUpdateBean.getPendientesMD5());
                break;

            case Constants.DATABASE_UNIDADES:
                args.put(CATALOGS_NUMBER, notificationsUpdateBean.getUnidadesNumber());
                args.put(CATALOGS_MD5, notificationsUpdateBean.getUnidadesMD5());
                break;

            case Constants.DATABASE_INV_SUPPLIES:
                args.put(CATALOGS_NUMBER, notificationsUpdateBean.getInvSuppliesNumber());
                args.put(CATALOGS_MD5, notificationsUpdateBean.getInvSuppliesMD5());
                break;

            case Constants.DATABASE_DIRECCIONES:
                args.put(CATALOGS_NUMBER, notificationsUpdateBean.getDireccionesNumber());
                args.put(CATALOGS_MD5, notificationsUpdateBean.getDireccionesMD5());
                break;

            case Constants.DATABASE_ENVIOSPEND:
                args.put(CATALOGS_NUMBER, notificationsUpdateBean.getEnviosPendNumber());
                args.put(CATALOGS_MD5, notificationsUpdateBean.getEnviosPendMD5());
                break;

            case Constants.DATABASE_RECEPCIONES:
                args.put(CATALOGS_NUMBER, notificationsUpdateBean.getRecepcionesNumber());
                args.put(CATALOGS_MD5, notificationsUpdateBean.getRecepcionesMD5());
                break;
        }

    	String[] conditional = new String[]{String.valueOf(type)};
    	return db.update(CATALOGS_DB_NAME, args, CATALOGS_ID_CATALOG + "=?", conditional);
    }

    public long setFailsFound(FailsFoundBean failsFoundBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(FAILS_FOUND_ID, failsFoundBean.getId());
        initialValues.put(FAILS_FOUND_DESC, failsFoundBean.getDesc());
        initialValues.put(FAILS_FOUND_ID_FATHER, failsFoundBean.getIdFather());
        initialValues.put(FAILS_FOUND_ID_CLIENT, failsFoundBean.getIdClient());
        return db.insert(FAILS_FOUND_DB_NAME, null, initialValues);
    }

    public long setViaticos(ViaticosBean viaticosBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(VIATICOS_ID_VIATICO, viaticosBean.getIdViatico());
        initialValues.put(VIATICOS_DESC_VIATICO, viaticosBean.getDescViatico());
    	return db.insert(VIATICOS_DB_NAME, null, initialValues);
    }
    
    public long setSpareParts(SparePartsBean sparePartsBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(SPAREPARTS_ID_SPAREPART, sparePartsBean.getId());
        initialValues.put(SPAREPARTS_DESC_SPAREPART, sparePartsBean.getDescSparePart());
    	return db.insert(SPAREPARTS_DB_NAME, null, initialValues);
    }
    
    public long setInsumos(InsumosBean insumosBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(INSUMOS_ID_INSUMO, insumosBean.getId());
        initialValues.put(CLIENTES_ID_CLIENTE, insumosBean.getIdCliente());
        initialValues.put(INSUMOS_ID_TIPO_INSUMO, insumosBean.getIdTipoInsumo());
        initialValues.put(INSUMOS_DESC_INSUMO, insumosBean.getDescInsumo());
    	return db.insert(INSUMOS_DB_NAME, null, initialValues);
    }
    
    public long setAlmacenes(AlmacenesBean almacenesBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(ALMACENES_ID_ALMACEN, almacenesBean.getId());
        initialValues.put(ALMACENES_DESC_ALMACEN, almacenesBean.getDescAlmacen());
    	return db.insert(ALMACENES_DB_NAME, null, initialValues);
    }
    
    public long setMarcas(MarcasBean marcasBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MARCAS_ID_MARCA, marcasBean.getId());
        initialValues.put(PRODUCTO_ID_PRODUCTO, marcasBean.getIdProducto());
        initialValues.put(MARCAS_DESC_MARCA, marcasBean.getDescMarca());
    	return db.insert(MARCAS_DB_NAME, null, initialValues);
    }
    
    public long setModeloSpareParts(MSparePartsBean mSparePartsBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MSPAREPARTS_ID_MSPAREPART, mSparePartsBean.getId());
        initialValues.put(MODELOS_ID_MODELO, mSparePartsBean.getIdModelo());
        initialValues.put(SPAREPARTS_ID_SPAREPART, mSparePartsBean.getIdSparePart());
    	return db.insert(MSPAREPARTS_DB_NAME, null, initialValues);
    }
    
    public long setModelos(ModelosBean modelosBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MODELOS_ID_MODELO, modelosBean.getId());
        initialValues.put(MARCAS_ID_MARCA, modelosBean.getIdMarca());
        initialValues.put(MODELOS_ID_GPRS, modelosBean.getIdGPRS());
        initialValues.put(MODELOS_DESC_MODELO, modelosBean.getDescModelo());
        initialValues.put(MODELOS_SKU, modelosBean.getSku());
    	return db.insert(MODELOS_DB_NAME, null, initialValues);
    }
    
    public long setDirecciones(DireccionesBean direccionesBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DIRECCIONES_ID_DIRECCION, direccionesBean.getId());
        initialValues.put(DIRECCIONES_DIRECCION, direccionesBean.getDescDireccion());
        initialValues.put(DIRECCIONES_COLONIA, direccionesBean.getColonia());
        initialValues.put(DIRECCIONES_POBLACION, direccionesBean.getPoblacion());
        initialValues.put(DIRECCIONES_ESTADO, direccionesBean.getEstado());
        initialValues.put(DIRECCIONES_IS_DEFAULT,direccionesBean.getIsDefault());
    	return db.insert(DIRECCIONES_DB_NAME, null, initialValues);
    }
    
    public long setProcessShipment(PackageShipmentBean packageShipmentBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PROCESS_SHIPMENT_ID, packageShipmentBean.getIdShipment());
        initialValues.put(PROCESS_SHIPMENT_COUNT, packageShipmentBean.getCount());
        initialValues.put(PROCESS_SHIPMENT_TYPE, packageShipmentBean.getType());
    	return db.insert(PROCESS_SHIPMENT_DB_NAME, null, initialValues);
    }
    
    public long setRecepciones(PackageShipmentBean packageShipmentBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(RECEPTIONS_ID_SHIPMENT, packageShipmentBean.getIdShipment());
        initialValues.put(RECEPTIONS_TYPE, packageShipmentBean.getType());
        initialValues.put(RECEPTIONS_COUNT, packageShipmentBean.getCount());
    	return db.insert(RECEPTIONS_DB_NAME, null, initialValues);
    }
    
    public long setServicios(ServiciosBean serviciosBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(SERVICES_ID_SERVICIO, serviciosBean.getId());
        initialValues.put(CLIENTES_ID_CLIENTE, serviciosBean.getIdCliente());
        initialValues.put(SERVICES_ID_TIPO_SERVICIO, serviciosBean.getIdTipoServicio());
        initialValues.put(SERVICES_ID_MONEDA, serviciosBean.getIdMoneda());
        initialValues.put(SERVICES_ID_TIPO_PRECIO, serviciosBean.getIdTipoPrecio());
        initialValues.put(SERVICES_IS_INS_REQ, serviciosBean.getIsInsumosRequired());
        initialValues.put(SERVICES_IS_CAU_SOL_REQ, serviciosBean.getIsCausaSolucionRequired());
        initialValues.put(SERVICES_IS_CAU_REQ, serviciosBean.getIsCausaRequired());
        initialValues.put(SERVICES_IS_SOL_REQ, serviciosBean.getIsSolucionRequired());
        initialValues.put(SERVICES_IS_TAS_REQ, serviciosBean.getIsTASRequired());
        initialValues.put(SERVICES_IS_OTOR_TAS_REQ, serviciosBean.getIsOtorganteTASRequired());
        initialValues.put(SERVICES_IS_NO_EQUIPO_REQ, serviciosBean.getIsNoEquipoRequired());
        initialValues.put(SERVICES_IS_NO_SERIE_REQ, serviciosBean.getIsNoSerieRequired());
        initialValues.put(SERVICES_IS_NO_INV_REQ, serviciosBean.getIsNoInventarioRequired());
        initialValues.put(SERVICES_ID_IS_MODELO_REQ, serviciosBean.getIsIDModeloRequired());
        initialValues.put(SERVICES_IS_FEC_LLEG_REQ, serviciosBean.getIsFecLlegadaRequired());
        initialValues.put(SERVICES_IS_FEC_LLE_TER_REQ, serviciosBean.getIsFecLlegadaTercerosRequired());
        initialValues.put(SERVICES_IS_FOLIO_SER_REQ, serviciosBean.getIsFolioServicioRequired());
        initialValues.put(SERVICES_IS_FEC_INI_ING_REQ, serviciosBean.getIsFecIniIngenieroRequired());
        initialValues.put(SERVICES_IS_FEC_FIN_ING_REQ, serviciosBean.getIsFecFinIngenieroRequired());
        initialValues.put(SERVICES_IS_OTOR_VOBO_REQ, serviciosBean.getIsOtorganteVoBoRequired());
        initialValues.put(SERVICES_IS_OT_VOBO_TER_REQ, serviciosBean.getIsOtorganteVoBoTercerosRequired());
        initialValues.put(SERVICES_IS_INT_SEN_REQ, serviciosBean.getIsIntensidadSenialRequired());
        initialValues.put(SERVICES_IS_SIM_REEM_REQ, serviciosBean.getIsSimReemplazadaRequired());
        initialValues.put(SERVICES_IS_FOL_SER_REC_REQ, serviciosBean.getIsFolioServicioRechazoRequired());
        initialValues.put(SERVICES_IS_OT_VOBO_REC_REQ, serviciosBean.getIsOtorganteVoBoRechazoRequired());
        initialValues.put(SERVICES_IS_FALLA_ENC_REQ, serviciosBean.getIsFallaEncontradaRequired());
        initialValues.put(SERVICES_IS_OT_VOBO_CLI_REQ, serviciosBean.getIsOtorganteVoBoClienteRequired());
        initialValues.put(SERVICES_IS_MOT_COBRO_REQ, serviciosBean.getIsMotivoCobroRequired());
        initialValues.put(SERVICES_IS_SOPOR_CLI_REQ, serviciosBean.getIsSoporteClienteRequired());
        initialValues.put(SERVICES_IS_OT_SOP_CLI_REQ, serviciosBean.getIsOtorganteSoporteClienteRequired());
        initialValues.put(SERVICES_IS_BOLETIN_REQ, serviciosBean.getIsBoletinRequired());
        initialValues.put(SERVICES_IS_CAD_CIE_ESC_REQ, serviciosBean.getIsCadenaCierreEscritaRequired());
        initialValues.put(SERVICES_IS_DOWNTIME, serviciosBean.getIsDowntime());
        initialValues.put(SERVICES_IS_CIERRE_PDA, serviciosBean.getIsCierrePDA());
        initialValues.put(SERVICES_IS_APLICACION_REQ, serviciosBean.getIsAplicacionRequired());
        initialValues.put(SERVICES_IS_VERSION_REQ, serviciosBean.getIsVersionRequired());
        initialValues.put(SERVICES_IS_CAJA_REQ, serviciosBean.getIsCajaRequired());
        initialValues.put(SERVICES_DESC_SERVICIO, serviciosBean.getDescServicio());
        initialValues.put(SERVICES_ID_AR_REOPEN, serviciosBean.getIdARReopen());
        initialValues.put(SERVICES_ID_AR_NEED_FILE, serviciosBean.getIdARNeedFile());
        initialValues.put(SERVICES_ID_AR_NO_CHECKUP, serviciosBean.getIdARNeedNoCheckUp());
        initialValues.put(SERVICES_IS_KIT_REQ, serviciosBean.getIsKitRequired());
        initialValues.put(SERVICES_KIT_SUPPLY, serviciosBean.getKitSupply());
        //ADD NEW FIELD (03/08/2017)
        initialValues.put(SERVICES_NEED_SHEET, serviciosBean.getmNeedSevicesSheet());
        initialValues.put(SERVICES_CALIDAD_BILLETE,serviciosBean.getmIsCalidadBillete());
        initialValues.put(SERVICES_CONDICION_SITE,serviciosBean.getmIsCondicionSite());
    	return db.insert(SERVICES_DB_NAME, null, initialValues);
    }
    
    public long setUnidades(UnidadesBean unidadesBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(UNIDADES_ID_UNIDAD, 			unidadesBean.getId());
        initialValues.put(UNIDADES_ID_STATUS_UNIDAD, 	unidadesBean.getIdStatusUnidad());
    	return db.insert(UNIDADES_DB_NAME, null, initialValues);
    }

    public long setInvSupply(SupplyBean supplyBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(INV_SUPPLIES_ID_SUPPLY, supplyBean.getIdSupply());
        initialValues.put(INV_SUPPLIES_DESC_CLIENT, supplyBean.getDescClient());
        initialValues.put(INV_SUPPLIES_DESC, supplyBean.getDescSupply());
        initialValues.put(INV_SUPPLIES_TOTAL, supplyBean.getCount());
        return db.insert(INV_SUPPLIES_DB_NAME, null, initialValues);
    }
    
    public long setUnidad(UnitBean unitBean, SQLiteDatabase db)
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(UNIDADES_ID_UNIDAD, unitBean.getId());
    	initialValues.put(UNIDAD_ID_SOL_REC , unitBean.getIdRequestCollection());
    	initialValues.put(PRODUCTO_ID_PRODUCTO , unitBean.getIdProduct());
    	initialValues.put(UNIDAD_IS_NUEVA , unitBean.getIsNew());
    	initialValues.put(UNIDAD_IS_DANIADA , unitBean.getIsBroken());
    	initialValues.put(UNIDAD_IS_RETIRO , unitBean.getIsWithdrawal());
    	initialValues.put(CLIENTES_ID_CLIENTE , unitBean.getIdClient());
    	initialValues.put(UNIDAD_ID_TIPO_PRODUCTO , unitBean.getIdProductType());
    	initialValues.put(UNIDADES_ID_STATUS_UNIDAD , unitBean.getIdUnitStatus());
    	initialValues.put(UNIDAD_ID_USUARIO_ALTA , unitBean.getIdNewUser());
    	initialValues.put(UNIDAD_DESC_CLIENTE , unitBean.getDescClient());
    	initialValues.put(UNIDAD_DESC_MARCA , unitBean.getDescBrand());
    	initialValues.put(UNIDAD_DESC_MODELO , unitBean.getDescModel());
    	initialValues.put(UNIDAD_NO_SERIE , unitBean.getNoSerie());
    	initialValues.put(UNIDAD_NO_INVENTARIO , unitBean.getNoInventory());
    	initialValues.put(UNIDAD_NO_IMEI , unitBean.getNoIMEI());
    	initialValues.put(UNIDAD_NO_SIM , unitBean.getNoSim());
    	initialValues.put(UNIDAD_NO_EQUIPO , unitBean.getNoEquipment());
    	initialValues.put(UNIDAD_DESC_LLAVE , unitBean.getDescKey());
    	initialValues.put(UNIDAD_DESC_SOFTWARE , unitBean.getDescSoftware());
    	initialValues.put(UNIDAD_POSICION_INVENTARIO , unitBean.getInventoryPos());
    	initialValues.put(UNIDAD_DESC_STATUS_UNIDAD , unitBean.getDescUnitStatus());
    	initialValues.put(UNIDAD_STATUS , unitBean.getStatus());
    	initialValues.put(UNIDAD_FEC_ALTA , unitBean.getNewDate());
    	initialValues.put(UNIDAD_DESC_ACCESORIO , unitBean.getDescAccesory());
    	initialValues.put(UNIDAD_DESCRIPCION_ACC , unitBean.getDescriptionAccesory());
    	initialValues.put(UNIDAD_STATUS_ACCESORIO , unitBean.getStatusAccesory());
        initialValues.put(UNIDAD_CONN, unitBean.getDescConnectivity());
    	return db.insert(UNIDAD_DB_NAME, null, initialValues);
    }
    
    public long setCausasRechazo(CausasRechazoBean causasRechazoBean, SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(CAUREC_ID_CAUREC, causasRechazoBean.getId());
        initialValues.put(CLIENTES_ID_CLIENTE, causasRechazoBean.getIdCliente());
        initialValues.put(CAUREC_DESC_CAUREC, causasRechazoBean.getDescCausaRechazo());
    	return db.insert(CAUREC_DB_NAME, null, initialValues);
    }
    
    public long setEspecificacionCausasRechazo(EspecificaCausasRechazoBean especificacionCausasRechazoBean,
        SQLiteDatabase db)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(ESPCAUREC_ID_ESPCAUREC, especificacionCausasRechazoBean.getId());
        initialValues.put(ESPCAUREC_ID_CAURECPARENT, especificacionCausasRechazoBean.getIdCausaRechazoParent());
        initialValues.put(ESPCAUREC_DESC_ESPCAUREC, especificacionCausasRechazoBean.getDescEspeficicacionCausaRechazo());
    	return db.insert(ESPCAUREC_DB_NAME, null, initialValues);
    }
    
    public long setCausas(CausasBean causasBean, SQLiteDatabase db)
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(CAUSAS_ID_CAUSA, causasBean.getId());
    	initialValues.put(CAUSAS_ID_CLIENTE, causasBean.getIdCliente());
    	initialValues.put(CAUSAS_CLAVE, causasBean.getClave());
    	initialValues.put(CAUSAS_DESC_CAUSA, causasBean.getDescCausa());
    	
    	return db.insert(CAUSAS_DB_NAME, null, initialValues);
    }
    
    public long setSoluciones(SolucionesBean solucionesBean, SQLiteDatabase db)
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(SOLUCIONES_ID_SOLUCION, solucionesBean.getId());
    	initialValues.put(SOLUCIONES_ID_CLIENTE, solucionesBean.getIdCliente());
    	initialValues.put(SOLUCIONES_CLAVE, solucionesBean.getClave());
    	initialValues.put(SOLUCIONES_IS_EXITO, solucionesBean.getIsExito());
    	initialValues.put(SOLUCIONES_DESC_SOLUCION, solucionesBean.getDescSolucion());
    	return db.insert(SOLUCIONES_DB_NAME, null, initialValues);
    }
    
    public long setGrupos(GruposBean gruposBean, SQLiteDatabase db)
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(GRUPOS_ID_GRUPO, gruposBean.getId());
    	initialValues.put(GRUPOS_DESC_GRUPO, gruposBean.getDescGrupo());
    	return db.insert(GRUPOS_DB_NAME, null, initialValues);
    }
    
    public long setGruposClientes(GruposClientesBean gruposClientesBean, SQLiteDatabase db)
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(GRUPOSCLI_ID_GRUPOCLI, gruposClientesBean.getId());
    	initialValues.put(GRUPOS_ID_GRUPO, gruposClientesBean.getIdGrupo());
    	initialValues.put(CLIENTES_ID_CLIENTE, gruposClientesBean.getIdCliente());
    	return db.insert(GRUPOSCLI_DB_NAME, null, initialValues);
    }
    
    public long setCodigosIntervencion0(CodigosIntervencion0Bean codigosIntervencion0Bean, SQLiteDatabase db)
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(CODIGOS0_ID_CODIGO, codigosIntervencion0Bean.getId());
    	initialValues.put(CODIGOS0_CLAVE_CODIGO, codigosIntervencion0Bean.getClaveCodigo());
    	initialValues.put(CLIENTES_ID_CLIENTE, codigosIntervencion0Bean.getIdCliente());
    	initialValues.put(CODIGOS0_REPORTA_INSTALACION, codigosIntervencion0Bean.getReportaInstalacion());
    	initialValues.put(CODIGOS0_DESC_CODIGO, codigosIntervencion0Bean.getDescCodigo());
    	return db.insert(CODIGOS0_DB_NAME, null, initialValues);
    }
    
    public long setCodigosIntervencion1(CodigosIntervencion1Bean codigosIntervencion1Bean, SQLiteDatabase db)
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(CODIGOS1_ID_CODIGO, codigosIntervencion1Bean.getId());
    	initialValues.put(CODIGOS1_CLAVE_CODIGO, codigosIntervencion1Bean.getClaveCodigo());
    	initialValues.put(CLIENTES_ID_CLIENTE, codigosIntervencion1Bean.getIdCliente());
    	
    	if(codigosIntervencion1Bean.getIdParent0().equals("") || codigosIntervencion1Bean.getIdParent0() == null)
    		initialValues.put(CODIGOS1_ID_PARENT_0, 	0);
    	else
    		initialValues.put(CODIGOS1_ID_PARENT_0, 	codigosIntervencion1Bean.getIdParent0());
    	
    	initialValues.put(CODIGOS1_DESC_CODIGO, 	codigosIntervencion1Bean.getDescCodigo());
    	
    	return db.insert(CODIGOS1_DB_NAME, null, initialValues);
    }
    
    public long setCodigosIntervencion2(CodigosIntervencion2Bean codigosIntervencion2Bean, SQLiteDatabase db )
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(CODIGOS2_ID_CODIGO, codigosIntervencion2Bean.getId());
    	initialValues.put(CODIGOS2_CLAVE_CODIGO, codigosIntervencion2Bean.getClaveCodigo());
    	initialValues.put(CLIENTES_ID_CLIENTE, codigosIntervencion2Bean.getIdCliente());
    	
    	if(codigosIntervencion2Bean.getIdParent1().equals("") || codigosIntervencion2Bean.getIdParent1() == null)
    		initialValues.put(CODIGOS2_ID_PARENT_1, 0);
    	else
    		initialValues.put(CODIGOS2_ID_PARENT_1, codigosIntervencion2Bean.getIdParent1());
    	initialValues.put(CODIGOS2_DESC_CODIGO, codigosIntervencion2Bean.getDescCodigo());
    	
    	return db.insert(CODIGOS2_DB_NAME, null, initialValues);
    }
    
    public long setServCau( ServiciosCausasBean serviciosCausasBean, SQLiteDatabase db )
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(SERVCAU_ID_SERVCAU, serviciosCausasBean.getId());
    	initialValues.put(SERVICES_ID_SERVICIO, serviciosCausasBean.getIdServicio());
    	initialValues.put(CAUSAS_ID_CAUSA, serviciosCausasBean.getIdCausa());
    	return db.insert(SERVCAU_DB_NAME, null, initialValues);
    }
    
    public long setServSol( ServiciosSolucionesBean serviciosSolucionesBean, SQLiteDatabase db)
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(SERVSOL_ID_SERVSOL, serviciosSolucionesBean.getId());
    	initialValues.put(SERVICES_ID_SERVICIO, serviciosSolucionesBean.getIdServicio());
    	initialValues.put(SOLUCIONES_ID_SOLUCION, serviciosSolucionesBean.getIdSolucion());
    	return db.insert(SERVSOL_DB_NAME, null, initialValues);
    }
    
    public long setCliMod( ClienteModelosBean clienteModeloBean, SQLiteDatabase db )
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(CLIMOD_ID_CLIMOD, clienteModeloBean.getId());
    	initialValues.put(CLIENTES_ID_CLIENTE, clienteModeloBean.getIdCliente());
    	initialValues.put(MODELOS_ID_MODELO, clienteModeloBean.getIdModelo());
    	return db.insert(CLIMOD_DB_NAME, null, initialValues);
    }
    
    public long setTipoFalla( TipoFallaBean tipoFallaBean, SQLiteDatabase db )
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(TIPOFALLA_ID_TIPOFALLA, tipoFallaBean.getId());
    	initialValues.put(SERVICES_ID_SERVICIO, tipoFallaBean.getIdServicio());
    	initialValues.put(CLIENTES_ID_CLIENTE, tipoFallaBean.getIdCliente());
    	initialValues.put(TIPOFALLA_DESC_TIPOFALLA, tipoFallaBean.getDescTipoFalla());
    	return db.insert(TIPOFALLA_DB_NAME, null, initialValues);
    }
    
    public long setEspTiFa(EspecificacionTipoFallaBean especificacionTipoFallaBean, SQLiteDatabase db)
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(ESPTIFA_ID_ESPTIFA, especificacionTipoFallaBean.getId());
    	initialValues.put(ESPTIFA_ID_FALLA_PARENT, especificacionTipoFallaBean.getIdFallaParent());
    	initialValues.put(ESPTIFA_DESC_ESPTIFA, especificacionTipoFallaBean.getDescEspecificacionFalla());
    	return db.insert(ESPTIFA_DB_NAME, null, initialValues);
    }
    
    public long setIngenieros(IngenierosBean ingenierosBean, SQLiteDatabase db)
    {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(INGENIEROS_ID_INGENIERO, 		ingenierosBean.getId());
    	initialValues.put(INGENIEROS_NOMBRECOMPLETO, ingenierosBean.getNombreCompleto());
    	return db.insert(INGENIEROS_DB_NAME, null, initialValues);
    }
    
    // Sección para borrar tablas
    public void deleteRequests(String type, SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + REQUESTS_DB_NAME + " WHERE " + CATALOGS_ID_CATALOG + " = " + type);
    }

    public void deleteClients(SQLiteDatabase db)
    {
        db.execSQL("DELETE FROM " + CLIENTS_DB_NAME);
    }

    public void deleteConnectivity(SQLiteDatabase db)
    {
        db.execSQL("DELETE FROM " + CONNECTIVITY_DB_NAME);
    }

    /*
    * Borrar tabla de causa retiro 22/03/2016 JDOS
    * */
    public void deleteCausaRetiro(SQLiteDatabase db){
        db.execSQL("DELETE FROM " + CAUSA_RETIRO_DB_NAME);
    }

    /*
    * JDOS Borrar tablas 30/01/2018*/
    public void deleteCalidadBillete(SQLiteDatabase db){
        db.execSQL("DELETE FROM " + CALIDAD_BILLETE);
    }

    public void deleteClienteCalidadBillete(SQLiteDatabase db){
        db.execSQL("DELETE FROM " + CLIENTE_CALIDAD_BILLETE);
    }

    public void deleteClienteCondicionSite(SQLiteDatabase db){
        db.execSQL("DELETE FROM " + CLIENTE_CONDICIONES_SITE);
    }

    public void deleteCondicionSite(SQLiteDatabase db){
        db.execSQL("DELETE FROM " + CONDICIONES_SITE);
    }
    /*
    * FIN*/
    public void deleteEtiquetas(SQLiteDatabase db){
        db.execSQL("DELETE FROM " + ETIQUETAS_BD_NAME);
    }

    public void deleteSoftware(SQLiteDatabase db)
    {
        db.execSQL("DELETE FROM " + SOFTWARE_DB_NAME);
    }

    public void deleteFailsFound(SQLiteDatabase db)
    {
        db.execSQL("DELETE FROM " + FAILS_FOUND_DB_NAME);
    }

    public void deleteStatus(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + STATUS_DB_NAME);
    }
    
    public void deleteExtraCatalogs(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + EXTRA_DB_NAME);
    }
    
    public void deleteCambioStatus(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + CAMBIO_DB_NAME);
    }
    
    public void deleteProductosStatus(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + PRODUCTO_DB_NAME);
    }
    
    public void deleteClientesCambios(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + CLIENTES_DB_NAME);
    }
    
    public void deleteAllListaViaticos(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + LISTAVIATICOS_DB_NAME);
    }
    
    public void deleteAllListaUnidades(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + LISTAUNIDADES_DB_NAME);
    }
    
    public void deleteAllSKU(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + SKU_DB_NAME);
    }
    
    public void deleteListaViaticos(String id, String costo, SQLiteDatabase db)
    {
    	db.execSQL(	"DELETE FROM " + LISTAVIATICOS_DB_NAME 
    			  + " WHERE " + VIATICOS_ID_VIATICO + " = " + id 	 
    			  + " AND "   + LISTAVIATICOS_COSTO + " = " + costo);
    }
    
    public void deleteListaUnidades(String id, String descripcion, SQLiteDatabase db)
    {
    	db.execSQL(	"DELETE FROM " 	+ LISTAUNIDADES_DB_NAME 
    			  + " WHERE " 		+ UNIDADES_ID_UNIDAD 			+ " = " + id 	 
    			  + " AND "   		+ LISTAUNIDADES_DESC_UNIDAD 	+ " = '" + descripcion + "'");
    }
    
    public void deleteViaticos(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + VIATICOS_DB_NAME);
    }
    
    public void deleteSpareParts(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + SPAREPARTS_DB_NAME);
    }
    
    public void deleteInsumos(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + INSUMOS_DB_NAME);
    }
    
    public void deleteAlmacenes(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + ALMACENES_DB_NAME);
    }
    
    public void deleteMarcas(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + MARCAS_DB_NAME);
    }
    
    public void deleteMSpareParts(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + MSPAREPARTS_DB_NAME);
    }
    
    public void deleteModelos(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + MODELOS_DB_NAME);
    }
    
    public void deleteDirecciones(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + DIRECCIONES_DB_NAME);
    }
    
    public void deleteProcessShipment(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + PROCESS_SHIPMENT_DB_NAME);
    }
    
    public void deleteRecepciones(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + RECEPTIONS_DB_NAME);
    }
    
    public void deleteServicios(SQLiteDatabase db)
    {
    	db.execSQL("DELETE FROM " + SERVICES_DB_NAME);
    }
    
    public void deleteUnidades(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + UNIDADES_DB_NAME);
    }
    
    public void deleteUnidad(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + UNIDAD_DB_NAME);
    }

    public void deleteInvSupplies(SQLiteDatabase db)
    {
        db.execSQL("DELETE FROM " + INV_SUPPLIES_DB_NAME);
    }
    
    public void deleteCausas(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + CAUSAS_DB_NAME);
    }
    
    public void deleteCausasRechazo(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + CAUREC_DB_NAME);
    }
    
    public void deleteEspecificacionCausasRechazo(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + ESPCAUREC_DB_NAME);
    }
    
    public void deleteSoluciones(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + SOLUCIONES_DB_NAME);
    }
    
    public void deleteGrupos(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + GRUPOS_DB_NAME);
    }
    
    public void deleteGruposClientes(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + GRUPOSCLI_DB_NAME);
    }
    
    public void deleteCodigosIntervencion0(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + CODIGOS0_DB_NAME);
    }
    
    public void deleteCodigosIntervencion1(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + CODIGOS1_DB_NAME);
    }
    
    public void deleteCodigosIntervencion2(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + CODIGOS2_DB_NAME);
    }
    
    public void deleteServCau(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + SERVCAU_DB_NAME);
    }
    
    public void deleteServSol(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + SERVSOL_DB_NAME);
    }
    
    public void deleteCliMod(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + CLIMOD_DB_NAME);
    }
    
    public void deleteTipoFalla(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + TIPOFALLA_DB_NAME);
    }
    
    public void deleteEspTiFa(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + ESPTIFA_DB_NAME);
    }
    
    public void deleteIngenieros(SQLiteDatabase db)
	{
    	db.execSQL("DELETE FROM " + INGENIEROS_DB_NAME);
    }

}