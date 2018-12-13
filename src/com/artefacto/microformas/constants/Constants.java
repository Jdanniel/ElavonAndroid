package com.artefacto.microformas.constants;

public final class Constants
{
    public static String BASE_URL_LIVE	= "http://smc.microformas.com.mx/BB/Android_Beta";
//    public static String BASE_URL_TEST	= "http://smc.microformas.com.mx/BB/Android_VERSION_PRUEBA";
//    public static String BASE_URL_TEST	= "http://smc.microformas.com.mx/MIC3BIS/Android";
//    public static String BASE_URL_TEST	= "http://smc.microformas.com.mx/MIC3_LAB/Android";
//    public static String BASE_URL_TEST  = "http://sgs-test.microformas.com.mx/mic3/android";
    public static String BASE_URL_TEST  = "http://apps.microformas.com.mx:81/mic3/android";
    public static String BASE_MODE_TEST = "test";
    public static String BASE_MODE_LIVE = "live";

	public static String APP_VERSION = BASE_MODE_LIVE;

	public static final String PREF_APP_MODE                    = "app_mode";
	public static final String PREF_APP_URL_TEST                = "app_url_test";
	public static final String PREF_APP_URL_LIVE                = "app_url_live";
	public static final String PREF_ACTIVITY_CHECKER			= "activity_checker";
	public static final String PREF_LOGIN_ACTIVITY				= "login_activity";
	public static final String PREF_MAIN_ACTIVITY				= "main_activity";
	public static final String PREF_NUEVAS_LIST_ACTIVITY		= "nuevas_list_activity";
	public static final String PREF_ABIERTAS_LIST_ACTIVITY		= "abiertas_list_activity";
	public static final String PREF_CERRADAS_LIST_ACTIVITY		= "cerradas_list_activity";
	public static final String PREF_PENDIENTES_LIST_ACTIVITY	= "pendientes_list_activity";
	public static final String PREF_DOCUMENTS_LIST_ACTIVITY		= "documents_list_activity";
	public static final String PREF_NUEVAS_DETAIL_ACTIVITY		= "nuevas_detail_activity";
	public static final String PREF_ABIERTAS_DETAIL_ACTIVITY	= "abiertas_detail_activity";
	public static final String PREF_CERRADAS_DETAIL_ACTIVITY	= "cerradas_detail_activity";
	public static final String PREF_PENDIENTES_DETAIL_ACTIVITY	= "pendientes_detail_activity";
	public static final String PREF_DOCUMENTS_DETAIL_ACTIVITY	= "documents_detail_activity";
	public static final String PREF_ACTUALIZACION_ACTIVITY		= "ActualizacionActivity";
	public static final String PREF_INSTALACION_ACTIVITY		= "InstalacionActivity";
	public static final String PREF_LAST_REQUEST_VISITED		= "last_request_visited";
	
	public static final String PREF_CONFIG_USER		= "UserConfig";
	public static final String PREF_CONFIG_SERVER	= "config_server";
	public static final String PREF_USER_ID			= "user_id";
		
	public static final String PREF_NEWS_MD5			= "nuevas_md5";
	public static final String PREF_OPENED_MD5			= "abiertas_md5";
	public static final String PREF_CLOSED_MD5			= "cerradas_md5";
	public static final String PREF_PENDINGS_MD5		= "pendientes_md5";
	public static final String PREF_UNITS_MD5			= "unidades_md5";
	public static final String PREF_INV_SUPPLIES_MD5	= "inv_supplies_md5";
	public static final String PREF_ADDRESSES_MD5		= "direcciones_md5";
	public static final String PREF_PENDINGS_SENT_MD5	= "envios_pendientes_md5";
	public static final String PREF_RECEPTIONS_MD5		= "recepciones_md5";
		
	public static final String PREF_NEWS_NUMBER				= "nuevas_number";
	public static final String PREF_OPENED_NUMBER			= "abiertas_number";
	public static final String PREF_CLOSED_NUMBER			= "cerradas_number";
	public static final String PREF_PENDINGS_NUMBER			= "pendientes_numer";
	public static final String PREF_UNITS_NUMBER			= "unidades_numer";
	public static final String PREF_INV_SUPPLIES_NUMBER		= "inv_supplies_number";
	public static final String PREF_ADDRESSES_NUMBER		= "direcciones_numer";
	public static final String PREF_PENDINGS_SENT_NUMBER	= "envios_pendientes_number";
	public static final String PREF_RECEPTIONS_NUMBER		= "recepciones_number";
		
	public static final String PREF_STATUS_DATE					= "status_date";
	public static final String PREF_PRODUCTS_DATE				= "productos_date";
	public static final String PREF_CHANGES_DATE				= "cambios_date";
	public static final String PREF_EXPENSES_DATE				= "viaticos_date";
	public static final String PREF_SPARE_PARTS_DATE			= "spare_parts_date";
	public static final String PREF_INPUTS_DATE					= "insumos_date";
	public static final String PREF_STORES_DATE					= "almacenes_date";
	public static final String PREF_BRANDS_DATE					= "marcas_date";
	public static final String PREF_MSPARE_PARTS_DATE			= "mspare_part_date";
	public static final String PREF_MODELS_DATE					= "modelos_date";
	public static final String PREF_SERVICES_DATE 				= "servicios_date";
	public static final String PREF_CAUSES_DATE 				= "causas_date";
	public static final String PREF_REJECTION_CAUSES_DATE 		= "causas_rechazo_date";
	public static final String PREF_SPEC_REJECTION_CAUSES_DATE	= "especifica_causas_rechazo_date";
	public static final String PREF_SOLUTIONS_DATE				= "soluciones_date";
	public static final String PREF_GROUPS_DATE					= "grupos_date";
	public static final String PREF_GROUPS_CLIENT_DATE			= "gruposclientes_date";
	public static final String PREF_CODES_0_DATE				= "codigos0_date";
	public static final String PREF_CODES_1_DATE				= "codigos1_date";
	public static final String PREF_CODES_2_DATE				= "codigos2_date";
	public static final String PREF_CLIENTS_MOD_DATE			= "clientesmod_date";
	public static final String PREF_SERVSOL_DATE				= "servsol_date";
	public static final String PREF_FAIL_TYPE_DATE				= "tipofalla_date";
	public static final String PREF_SPEC_FAIL_TYPE_DATE			= "esptipofalla_date";
	public static final String PREF_SERVCAU_DATE				= "servcau_date";
	public static final String PREF_ENGINEERS_DATE 				= "ingenieros_date";
	public static final String PREF_DATE_CLIENTS 				= "clients_date";
	public static final String PREF_DATE_CONNECTIVITY			= "connectivity_date";
	public static final String PREF_DATE_SOFTWARE 				= "software_Date";
	public static final String PREF_DATE_CAUSA_RETIRO 			= "causa_retiro_Date";
	public static final String PREF_DATE_ETIQUETAS				= "etiquetas_date";
	public static final String PREF_DATE_FAILS_FOUND			= "fails_found_date";
	/*JDOS Se agrega Campos nuevos*/
	public static final String PREF_DATE_TICKET_QUALITY			= "ticket_quality_date";
	public static final String PREF_DATE_SITE_CONDITION			= "site_condition_date";
	public static final String PREF_DATE_CLIENT_TICKET_QUALITY	= "client_ticket_quality_date";
	public static final String PREF_DATE_CLIENT_SITE_CONDITION	= "client_site_condition_date";

	public static final String PREF_CONEXIONES_PENDIENTES = "pendingConn";
	
	public static final String PREF_INST_INFO_OBTAINED 			= "instInfoObtained";
	public static final String PREF_INST_IS_REQ 				= "instIsReq";
	public static final String PREF_INST_NEGOCIO 				= "instNegocio";
	public static final String PREF_INST_EDIT 					= "instEdit";
	public static final String PREF_INST_CLIENTE 				= "instCliente";
	public static final String PREF_INST_TIPO_PRODUCTO 			= "infoProducto";
	public static final String PREF_INST_UNIDADES_INSTALADAS	= "unidadesInstaladas";
	
	public static final String PREF_SUST_EDIT 		= "sustEdit";
	public static final String PREF_SUST_IS_DANIADA = "sustIsDaniada";
	public static final String PREF_SUST_NO_EQUIPO 	= "sustNoEquipo";

	public static final String PREF_RET_ID_CLIENTE 	= "retIdCliente";
	public static final String PREF_RET_ID_NEGOCIO 	= "retIdNegocio";
	public static final String PREF_RET_EDIT 		= "retEdit";
	public static final String PREF_RET_IS_DANIADA 	= "retIsDaniada";
	
	//Claves para identificar estatus de solicitudes
	public static final String REQUEST_APPROVED = "13";
	public static final String REQUEST_REFUSED  = "2";
	
	//ID de las solicitudes
	public static final int DATABASE_NUEVAS 		= 0;
	public static final int DATABASE_ABIERTAS 		= 1;
	public static final int DATABASE_CERRADAS 		= 2;
	public static final int DATABASE_PENDIENTES 	= 3;
	public static final int DATABASE_UNIDADES 		= 4;
	public static final int DATABASE_DIRECCIONES	= 7;
	public static final int DATABASE_ENVIOSPEND		= 8;
	public static final int DATABASE_RECEPCIONES	= 9;
	public static final int DATABASE_DOCUMENTS		= 10;
	public static final int DATABASE_INV_SUPPLIES	= 11;
	
	//ID de los catálogos extras
	public static final int CATALOG_STATUS				= 0;
	public static final int CATALOG_PRODUCTOS 			= 1;
	public static final int CATALOG_CAMBIOS				= 2;
	public static final int CATALOG_VIATICOS			= 3;
	public static final int CATALOG_SPARE_PARTS			= 4;
	public static final int CATALOG_INSUMOS				= 5;
	public static final int CATALOG_ALMACENES			= 6;
	public static final int CATALOG_MARCAS				= 7;
	public static final int CATALOG_MSPARE_PARTS		= 8;
	public static final int CATALOG_MODELOS				= 9;
	public static final int CATALOG_SERVICIOS			= 11;
	public static final int CATALOG_CAUSAS				= 12;
	public static final int CATALOG_CAUSASRECHAZO		= 13;
	public static final int CATALOG_ESPECCAUREC			= 14;
	public static final int CATALOG_SOLUCIONES			= 15;
	public static final int CATALOG_GRUPOS				= 16;
	public static final int CATALOG_GRUPOSCLIENTES		= 17;
	public static final int CATALOG_CODIGOS0			= 18;
	public static final int CATALOG_CODIGOS1			= 19;
	public static final int CATALOG_CODIGOS2			= 20;
	public static final int CATALOG_CLIENTESMOD			= 21;
	public static final int CATALOG_SERVSOL				= 22;
	public static final int CATALOG_TIPOFALLA			= 23;
	public static final int CATALOG_ESPTIPOFALLA		= 24;
	public static final int CATALOG_SERVCAU				= 25;
	public static final int CATALOG_INGENIEROS			= 26;
	public static final int CATALOG_CLIENTS				= 27;
	public static final int CATALOG_CONNECTIVITY		= 28;
	public static final int CATALOG_SOFTWARE        	= 29;
	public static final int CATALOG_FAILS_FOUNDS		= 30;
	public static final int CATALOG_CAUSAS_RETIRO		= 31;
	public static final int CATALOG_ETIQUETAS			= 32;
	public static final int CATALOG_CALIDAD_BILLETE		= 33;
	public static final int CATALOG_CONDICION_SITE 		= 34;
	public static final int CATALOG_C_CALIDAD_BILLETE	= 35;
	public static final int CATALOG_C_CONDICION_SITE 	= 36;

	//Constantes de status
	public static final String STATUS_EN_SITIO  			= "5";
	public static final String STATUS_PENDIENTE_REFACCION 	= "26";
	public static final String STATUS_PENDIENTE_VIATICOS  	= "28";
	public static final String STATUS_PENDIENTE_TERMINAL  	= "12";
	public static final String STATUS_PENDIENTE_SPAREPARTS  = "29";
	public static final String STATUS_PENDIENTE_INSUMO  	= "11";
	
	//Constantes para búsqueda en RefaccionesActivity
	public static final int 	REFACCIONES_TIPO_STORAGE 	= 0;
	public static final int 	REFACCIONES_TIPO_BRAND 		= 1;
	public static final int 	REFACCIONES_TIPO_MODEL 		= 2;
	
	public static final String 	REFACCIONES_ALMACEN_TEXT 	= "almacen_text";
	public static final String 	REFACCIONES_ALMACEN_ID 		= "almacen_id";
	public static final String 	REFACCIONES_MARCA_TEXT 		= "marca_text";
	public static final String 	REFACCIONES_MARCA_ID 		= "marca_id";
	public static final String 	REFACCIONES_MODELO_TEXT 	= "modelo_text";
	public static final String 	REFACCIONES_MODELO_ID 		= "modelo_id";
	public static final String 	REFACCIONES_CANTIDAD_TEXT 	= "refaccion_cantidad_text";
	
	//Constantes para unidad de instalacion
	public static final String UNIDAD_INSTALACION_ID = "unidad_instalacion_id";
	public static final String UNIDAD_INSTALACION_TEXT = "unidad_instalacion_text";
	
	//Constantes para envios asincronos
	public static final String ASYNC_CHANGE_STATUS 		= "0";
	public static final String ASYNC_SEND_COMMENT 		= "1";
	public static final String ASYNC_SEND_SPARES 		= "2";
	public static final String ASYNC_SEND_VIATICOS 		= "3";
	public static final String ASYNC_UPLOAD_IMAGE 		= "4";
	public static final String ASYNC_UPLOAD_PDF 		= "5";
	public static final String ASYNC_UPLOAD_PDF_CLOSE 	= "6";
	
	public static final String ASYNC_SEPARATOR_PARAMETER 	= "//////////";
	public static final String ASYNC_SEPARATOR_REQUEST		= "----------";
	
	//Constantes para DetailActivity
	public static final String SUCCESS_CLOSURE 	= "SUCCESS";
	public static final String REJECT_CLOSURE 	= "REJECT";
	
	public static final String VALIDATION_TRUE 			= "SI";
	public static final String VALIDATION_INSTALACION 	= "INSTALACION";
	public static final String VALIDATION_SUSTITUCION 	= "SUSTITUCION";
	public static final String VALIDATION_RETIRO 		= "RETIRO";
	public static final String VALIDATION_INSUMO 		= "INSUMO";
	// NEW POSIBILITY (17-10-2014)
	public static final String VALIDATION_FILE 			= "FILE";
	
	public static final int VALIDATE_NUM_INVALIDO 		= 0;
	public static final int VALIDATE_NUM_TRUE 			= 1;
	public static final int VALIDATE_NUM_INSTALACION 	= 2;
	public static final int VALIDATE_NUM_SUSTITUCION	= 3;
	public static final int VALIDATE_NUM_RETIRO 		= 4;
	public static final int VALIDATE_NUM_INSUMO 		= 5;
	// NEW POSIBILITY (17-10-2014)
	public static final int VALIDATE_NUM_FILE			= 6;
	
	//Constantes para Busqueda de Inventario
	public static final int BUSQUEDA_INVENTARIO_TIPO_NO_SERIE 	= 1;
	public static final int BUSQUEDA_INVENTARIO_TIPO_MODELO 	= 2;
	public static final int BUSQUEDA_INVENTARIO_TIPO_CLIENTE 	= 4;
	
	public static final int BUSQUEDA_INVENTARIO_TIPO_USO_NUEVAS 	= 1;
	public static final int BUSQUEDA_INVENTARIO_TIPO_USO_USADAS 	= 2;
	public static final int BUSQUEDA_INVENTARIO_TIPO_USO_DANIANDAS	= 4;
	public static final int BUSQUEDA_INVENTARIO_TIPO_USO_TODAS		= 7;
	
	//Constantes para usar en NuevoEnvio y AgregarUnidad
	public static final String AGREGAR_TYPE 			= "agregar_type";
	public static final String AGREGAR_UNIDAD 			= "agregar_unidad";
	public static final String AGREGAR_ALMACEN 			= "agregar_almacen";
	public static final String AGREGAR_INGENIERO		= "agregar_ingeniero";
	public static final String AGREGAR_INSUMO			= "agregar_insumo";
	public static final String AGREGAR_CLIENTE			= "agregar_cliente";

	public static final String AGREGAR_ALMACEN_TEXT 	= "agregar_almacen_text";
	public static final String AGREGAR_ALMACEN_ID 		= "agregar_almacen_id";
	public static final String AGREGAR_INGENIERO_TEXT 	= "agregar_ingeniero_text";
	public static final String AGREGAR_INGENIERO_ID		= "agregar_ingeniero_id";
	public static final String AGREGAR_LAST_CATALOG		= "agregar_last_catalog";
	
	//Constantes para Terminal List
	public static final String TERMINAL_ALMACENES		= "terminal_almacenes";
	public static final String TERMINAL_BRANDS 			= "terminal_marcas";
	public static final String TERMINAL_MODELS			= "terminal_modelos";
	public static final String TERMINAL_CONNECTIVITY	= "terminal_connectivity";
	public static final String TERMINAL_SOFTWARE = "terminal_application";
	public static final String SPAREPART_SPAREPARTS		= "sparepart_sparepart";
	public static final String INSUMO_ALMACENES			= "insumo_almacenes";
	public static final String INSUMO_INSUMOS			= "insumo_insumos";
	
	public static final String TERMINAL_ALMACENES_ID		= "terminal_almacenes_id";
	public static final String TERMINAL_ALMACENES_DESC		= "terminal_almacenes_desc";
	public static final String TERMINAL_MARCAS_ID			= "terminal_marcas_id";
	public static final String TERMINAL_MARCAS_DESC			= "terminal_marcas_desc";
	public static final String TERMINAL_MODELOS_ID			= "terminal_modelos_id";
	public static final String TERMINAL_MODELOS_DESC		= "terminal_modelos_desc";
//	public static final String TERMINAL_CONNECTIVITY_ID		= "terminal_connectivity_id";
//	public static final String TERMINAL_CONNECTIVITY_DESC	= "terminal_connectivity_desc";
//    public static final String TERMINAL_SOFTWARE_ID 		= "terminal_software_id";
//	public static final String TERMINAL_SOFTWARE_DESC	    = "terminal_software_desc";
	public static final String TERMINAL_ID_PRODUCT			= "terminal_id_product";
	public static final String TERMINAL_ID_CLIENT			= "terminal_id_client";
	public static final String TERMINAL_REQUEST_TYPE		= "terminal_request_type";
	public static final String SPAREPART_SPAREPART_ID		= "sparepart_sparepart_id";
	public static final String SPAREPART_SPAREPART_DESC		= "sparepart_sparepart_desc";
	public static final String INSUMO_INSUMOS_ID			= "insumo_insumos_id";
	public static final String INSUMO_INSUMOS_DESC			= "insumo_insumos_desc";
	public static final String INSUMO_ALMACENES_ID			= "insumo_almacenes_id";
	public static final String INSUMO_ALMACENES_DESC		= "insumo_almacenes_desc";
	public static final String INSUMO_INSUMOS_ID_CLIENTE	= "insumo_insumos_id_cliente";
	public static final String INSUMO_INSUMOS_ID_PRODUCT	= "insumo_insumos_id_producto";

	public static final String SEARCH_SHIPMENT_ID			= "shipment_id";
	public static final String SEARCH_SHIPMENT_ID_CLIENT	= "shipment_id_client";
	public static final String SEARCH_SHIPMENT_DESC			= "shipment_desc";
	public static final String SEARCH_SHIPMENT_CLIENT		= "shipment_client";
	public static final String SEARCH_SHIPMENT_COUNT		= "shipment_count";

	/*-------------------Inicia cambio 03/08/2017*/
	public static final String RESPONSE_PDF_CLOSE			= "response_pdf_close";

    private Constants()
    {   //this prevents even the native class from calling this sector as well :
        throw new AssertionError();
    }
}