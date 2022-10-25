

import os

items = os.listdir(".")

data = []
for names in items:
    if names.endswith(".json"):
        data.append(names)
print(data)
