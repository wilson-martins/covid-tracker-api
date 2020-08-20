# coding: utf-8
"""
Append new status hustory data to a given file
"""
import random

# VARIABLES
# Path to the .csv file
file = "./covid-tracker-api/src/main/resources/status_history_possible_cases.csv"

# Start case group
case_group = 14 

# Maximum number of case groups (including the old ones)
n_case_groups = 50


#--------------------------------------------------------------------------------
symptoms = [    
    "CURED",
    "POSITIVE",
    "SYMPTOMATIC",
    "POSSIBLY_INFECTED",
    "ASYMPTOMATIC"
]

def get_next(th):
    """ Decide if will proceede to next stage or not
    """
    sort = random.randint(0, 100)
    if (sort < th):
        return True
    return False


with open (file, "a+") as f:
    while (case_group < n_case_groups):
        next_group = False
        state = random.randint(0,4)
        pos = 1
        n_registries = random.randint(1, limit) 
        while (pos < n_registries + 1):
            f.write("{},{},{}\n".format(case_group, symptoms[state], pos))
            
            next_group = get_next(40)
            if (next_group):
                break
                
            if (get_next(50) and state > 0):
                state = state -1;
            pos += 1
        case_group += 1



with open (file, "r") as f:
    print (f.read())
