SELECT CAR_CODE 
FROM CAR AS C INNER JOIN VEHICLE_DETAILS AS V ON C.VEHICLE_CODE = V.VEHICLE_CODE
WHERE VEHICLE_MODEL LIKE 'chev'
AND VEHICLE_AC = 1; #insert any search details from the front-end