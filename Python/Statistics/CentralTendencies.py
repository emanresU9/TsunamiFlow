import math
from typing import List

def mean(a:List[float])->float:
    return sum(a)/len(set)
def median(a:List[float])->float:
    return a.sort()[len(a)/2]
def de_mean(a:List[float])->List[float]:
    _mean = mean(a)
    return [a_i - _mean for a_i in a]
def sum_of_squares(a:List[float]) -> float:
    return sum(a_i ** 2 for a_i in a)
def variance(a:List[float]) -> float:
    return sum_of_squares(de_mean(a))/(len(a)-1)
def standard_deviation(a:List[float])->float:
    return math.sqrt(variance(a))
def covariance(a:List[float],b:list[float])->float:
    return sum(a_i*b_i for a_i,b_i in zip(a,b))/(len(a)-1)
def correlation(a:List[float],b:List[float])->float:
    stdev_a = standard_deviation(a)
    stdev_b = standard_deviation(b)
    if stdev_a > 0 and stdev_b > 0:
        return covariance(a,b)/stdev_a/stdev_b
    else:
        return 0