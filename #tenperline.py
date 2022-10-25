#Kawika Ilac
#9/12/22
#CSCI151
#Patricia Duce
#tenperline.py

import stdio
lst = []
# Filter the input sequence of numbers between 0 and 99
while not stdio.isEmpty():
    value = stdio.readInt()
    if(value > 0 and value < 99):
        lst.append(value)

stdio.writeln(lst)
stdio.writeln()
l = len(lst)
# Writes 10 integers perline with columns aligned
for i in range(1,1+1,1):
    stdio.write(str(format(lst[i-1],'02d')) + "    ")
    if (i%10 == 0) :
        stdio.writeln()
