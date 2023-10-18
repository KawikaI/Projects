import praw
import config
import time

def bot_login():
    print("Logging in...")
                    # username = ''
    r = praw.Reddit(username = "",
                    # password = 
                    password = "",
                    # client_id = ''
                    client_id = "",
                    # client_secret = ''
                    client_secret = "",
                    user_agent = "Reddit Bot v0.1")
    print("Logged in!")

    return r

def run_bot(r):
    print("Obtaining 25 comments...")

    for comment in r.subreddit('kawika test').comments(limit=25):
        if "dog" in comment.body:
            print("String with \"dog\" found in comment " + comment.id)
            comment.reply("I also love dogs! [Here](https://www.bing.com/images/search?view=detailV2&ccid=Jf0NnGpH&id=3E84181E26DB2A4C34C2317A55262D7443B2CF55&thid=OIP.Jf0NnGpH2AhNM3BtwZufwwHaJ4&mediaurl=https%3a%2f%2fjooinn.com%2fimages%2fdog-67.jpg&cdnurl=https%3a%2f%2fth.bing.com%2fth%2fid%2fR.25fd0d9c6a47d8084d33706dc19b9fc3%3frik%3dVc%252byQ3QtJlV6MQ%26pid%3dImgRaw%26r%3d0&exph=3840&expw=2880&q=image+of+dog&simid=608033237880215406&FORM=IRPRST&ck=20B0BA3EB57D7F944DB7EE8BDDE9FEC7&selectedIndex=0&idpp=overlayview&ajaxhist=0&ajaxserp=0) is an image of one!")
            print("Replied to comment " + comment.id)



r = bot_login()
run_bot(r)
