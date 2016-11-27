# script for creating a new rental. 

# the script will require these inputs variables, handled by the front-end:
# inputID - the id of the customer making the rental
# inputcarcode - the car code of the desired rental car
# inputdate - the date of the rental
# inputduration - how many days the car is to be rented


# front-end must check for the following conditions:
# 1. specified customer_id must be an existing customer:
# 		can check if null with 
#		(SELECT CUSTOMER_ID FROM CUSTOMER WHERE CUSTOMER_ID = inputID)

# 2. specified car_code must exist AND car_quantity must be above 0
#		can check if null with 
#		(SELECT CAR_CODE FROM CAR WHERE CAR_CODE = inputcarcode AND CAR_QUANTITY > 0)

# 3. specifed date for rental must have not already passed
#		this may be handled on the front-end using system date

# 4. inputduration must be > 0
#


#if the conditions are met, execute the rental:

#create the basic rental
INSERT INTO RENTAL (`RENTAL_DATE`, `CUSTOMER_ID`) VALUES (inputdate, inputID) #the input variables
#get the rental code of the newly created rental for the details

#int rentnum = recent primary key
SELECT LAST_INSERT_ID() #should return the most recently inserted primary key (our new rental number)

#get the daily rental fee based on the car_code as float dailyprice
SELECT PRICE_RENTFEE
FROM CAR AS C 
INNER JOIN VEHICLE_DETAILS AS V
    on C.VEHICLE_CODE = V.VEHICLE_CODE
INNER JOIN PRICE AS P
    on P.PRICE_CODE = V.PRICE_CODE

#float totalprice = dailyprice*inputduration //total price of the rental given daily rental fee and number of days

INSERT INTO RENTAL_DETAILS (`RENTAL_NUMBER`, `CAR_CODE`, `DETAIL_FEE`, `DETAIL_DUEDATE`, `DETAIL_LATEFEE`) #return date is null until a return is made
VALUES (rentnum, inputcarcode, totalprice, inputdate + inputduration, 0); #latefee is always 0 at the being 

#and lastly, reduce the quantity of the cars by 1
UPDATE CAR
SET CAR_QUANTITY = CAR_QUANTITY - 1
WHERE CAR_CODE = inputcarcode




