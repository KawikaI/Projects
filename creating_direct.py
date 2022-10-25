

# Python program to explain os.makedirs() method
	
# importing os module
import os
	
# Leaf directory
directory = "test1"
	
# Parent Directories
parent_dir = "C:\\Users\\kawik\\OneDrive\\Codecademy"
	
# Path
path = os.path.join(parent_dir, directory)
	
# Create the directory
# 'Nikhil'
os.makedirs(path)
print("Directory '% s' created" % directory)
	
# Directory 'GeeksForGeeks' and 'Authors' will
# be created too
# if it does not exists
	
	
	
# Leaf directory
directory = "test2"
	
# Parent Directories
parent_dir = "C:\\Users\\kawik\\OneDrive\\Codecademy"
	
# mode
mode = 0o666
	
path = os.path.join(parent_dir, directory)
	
# Create the directory 'c'
	
os.makedirs(path, mode)
print("Directory '% s' created" % directory)
	
	

	
# If any of the intermediate level
# directory is missing
# os.makedirs() method will
# create them
	
# os.makedirs() method can be
# used to create a directory tree
