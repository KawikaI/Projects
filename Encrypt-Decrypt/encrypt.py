# import required module
from cryptography.fernet import Fernet


# key generation
key = Fernet.generate_key()

# string the key in a file
# The below code will generate a file named filekey.key
# with one line of string that is your key.
with open('filekey.key', 'wb') as filekey:
    filekey.write(key)

# opening the key
with open('filekey.key', 'rb') as filekey:
    key = filekey.read()
  
# using the generated key
fernet = Fernet(key)
  
# opening the original file to encrypt
with open('testimage.png', 'rb') as file:
    original = file.read()
      
# encrypting the file
encrypted = fernet.encrypt(original)
  
# opening the file in write mode and 
# writing the encrypted data
with open('testimage.png', 'wb') as encrypted_file:
    encrypted_file.write(encrypted)
