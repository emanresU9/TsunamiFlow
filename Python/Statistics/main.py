import math
from matplotlib import pyplot as plt
num_friends = [i for i in range(0,100,1)]
minutes = [100 - 100**(a_i/100) for a_i in num_friends]
plt.plot(num_friends, minutes, 'g-', lw="1")
plt.show()