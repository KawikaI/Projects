import stdio
import sys# Empty Input

if stdio.isEmpty():
    stdio.writeln('No Input')

    sys.exit()p = stdio.readInt();total = 1x = pxtotal = total

    # Read sequence of integers and finds longest consecutive run and length of the run

while not stdio.isEmpty():
    q = stdio.readInt()
    if q == p:
        total += 1
        else: p = q total = 1

    if total > xtotal: xtotal = total x = q
    # Display longest consecutive run and length of the run
    stdio.writeln('Longest run: ' + str(xtotal) + \ ' consecutive ' + str(x) + 's')
