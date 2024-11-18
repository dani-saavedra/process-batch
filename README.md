### Tiempo para insertar con JDBC un archivo de 2000 registros con batch de 100 registros:
2024-11-18T10:25:27.139-05:00: Comenzando inserción
2024-11-18T10:25:27.140-05:00  : Guardando batch
2024-11-18T10:25:44.192-05:00  : Guardando batch
2024-11-18T10:26:01.019-05:00  : Guardando batch
2024-11-18T10:26:17.852-05:00  : Guardando batch
2024-11-18T10:26:34.682-05:00  : Guardando batch
2024-11-18T10:26:51.396-05:00  : Guardando batch
2024-11-18T10:27:08.120-05:00  : Guardando batch
2024-11-18T10:27:24.864-05:00  : Guardando batch
2024-11-18T10:27:41.539-05:00  : Guardando batch
2024-11-18T10:27:58.241-05:00  : Guardando batch
2024-11-18T10:28:14.920-05:00  : Guardando batch
2024-11-18T10:28:31.580-05:00  : Guardando batch
2024-11-18T10:28:48.227-05:00  : Guardando batch
2024-11-18T10:29:04.948-05:00  : Guardando batch
2024-11-18T10:29:21.602-05:00  : Guardando batch
2024-11-18T10:29:38.548-05:00  : Guardando batch
2024-11-18T10:29:57.069-05:00  : Guardando batch
2024-11-18T10:30:13.926-05:00  : Guardando batch
2024-11-18T10:30:30.696-05:00  : Guardando batch
2024-11-18T10:30:47.362-05:00  : Guardando batch
2024-11-18T10:31:04.080-05:00  : finaliza inserción

Resultado: tiempo promedio de insertar 100 registros, 17 segundos. Tiempo por registro individual: 0.17 seg
tiempo total: 5 minutos con 37 segundos (336.941 segundos)


### Tiempo para insertar con JDBC un archivo de 2000 registros con batch de 500 registros:
2024-11-18T10:35:07.280-05:00: Comenzando inserción
2024-11-18T10:35:07.281-05:00: Guardando batch
2024-11-18T10:36:38.174-05:00: Guardando batch
2024-11-18T10:38:08.742-05:00: Guardando batch
2024-11-18T10:41:06.478-05:00: Guardando batch
2024-11-18T10:42:37.439-05:00: finaliza inserción


Resultado: tiempo promedio de insertar 500 registros, 120 segundos.Tiempo por registro individual: 0.24 seg
tiempo total: 7 minutos con 30 segundos (450.160 segundos)


Resultado: tiempo promedio de insertar de 1 registrar: 0.33 seg
tiempo total: 10 minutos con 54 segundos ( 654.559 segundos)
