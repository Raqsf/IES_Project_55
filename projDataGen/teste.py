import time
import datetime
from datetime import datetime
from datetime import timedelta 
from random import randint


minutes_to_sum =  7
minutes = 0
hours = 8
initial_date = datetime.now().replace(hour=0, minute=0, second=0, microsecond=0)
while True:
    time.sleep(0.3)
    if (minutes + minutes_to_sum) > 60:
        hours += 1
        minutes = 0
    minutes = (minutes + minutes_to_sum) % 60
    seconds = randint(0, 60)
        
    initial_date = initial_date.replace(hour=hours, minute=minutes, second=seconds)
    print(initial_date.timestamp())