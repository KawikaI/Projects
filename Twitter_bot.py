# import statements
import tweepy
import tkinter
from tkinter import Tk, Label, Entry, Button


# keys and access token
consumer_key = '1tmYL4psgWBBkYfth2S1d5K28'
consumer_secret = 'Nv1pycjklKzOdWX6RsSYmgkcffciyZeIBjYm6yDOllGVRIAfad'
access_token = '1158385063702290434-luWIzq6DWNlOmXygcTqUKw0Gm6Lymk'
access_token_secret = 'SBgsFcqAwuXJVKF4MmfGjWehHD3hqzL4D7y3SWdPd2SPP'

# Initializing the OAuthHandler with consumer key and secret for authentication
auth = tweepy.OAuthHandler(consumer_key, consumer_secret)

# Setting the access token and access token secret to the authentication handler
auth.set_access_token(access_token, access_token_secret)

# Initializing the Tweepy API object with the authentication handler
api = tweepy.API(auth)

# Verifying the provided credentials and getting the authenticated user's details
user = api.verify_credentials()

# Printing the authenticated user's name
print(user.name)

# Printing the authenticated user's location
print(user.location)

# Following all the followers of the authenticated user
for follower in tweepy.Cursor(api.get_followers()).items():
    # follow command
    follower.follow()
# prints the name of the user being followed
print("Followed everyone that is following " + user.name)

# Creating a tkinter window 
root = Tk()

# Creating UI elements for the tkinter window
label1 = Label(root, text="Search")
E1 = Entry(root, bd=5)

label2 = Label(root, text="Number of Tweets")
E2 = Entry(root, bd=5)

label3 = Label(root, text="Response")
E3 = Entry(root, bd=5)

label4 = Label(root, text="Reply?")
E4 = Entry(root, bd=5)

label5 = Label(root, text="Retweet?")
E5 = Entry(root, bd=5)

label6 = Label(root, text="Favorite?")
E6 = Entry(root, bd=5)

label7 = Label(root, text="Follow?")
E7 = Entry(root, bd=5)

# main function that excecutes when button is pressed
def mainFunction():
    search = E1.get()
    numberOfTweets = int(E2.get())
    phrase = E3.get()
    reply = E4.get()
    retweet = E5.get()
    favorite = E6.get()
    follow = E7.get()

    
# submit button for gui
submit = Button(root, text="Submit", command=mainFunction)

# Packing UI elements into the main window for display

# Packing the "Search" label and corresponding entry field
label1.pack()
E1.pack()

# Packing the "Number of Tweets" label and corresponding entry field
label2.pack()
E2.pack()

# Packing the "Response" label and corresponding entry field
label3.pack()
E3.pack()

# Packing the "Reply?" label and corresponding entry field
label4.pack()
E4.pack()

# Packing the "Retweet?" label and corresponding entry field
label5.pack()
E5.pack()

# Packing the "Favorite?" label and corresponding entry field
label6.pack()
E6.pack()

# Packing the "Follow?" label and corresponding entry field
label7.pack()
E7.pack()

# Packing the "Submit" button at the bottom of the window
submit.pack(side=tkinter.BOTTOM)

# Running the main event loop to display the tkinter window and handle user interactions
root.mainloop()