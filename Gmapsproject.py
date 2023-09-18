import datetime
import googlemaps
from twilio.rest import Client

def get_commmute_duration():
    home_address = "410 Aeneas St, Hot Springs, MT 59845, USA"
    work_address = "University of montana, 32 Campus Dr, Missoula, MT 59812, USA"

    google_maps_api_key = "YOUR_GOOGLE_MAPS_API_KEY"
    gmaps = googlemaps.Client(key=google_maps_api_key)

    directions = gmaps.directions(home_address, work_address)
    first_leg = directions[0]['legs'][0]
    duration = first_leg['duration']['text']
    return duration

def send_text_messages(message):
    twilio_account_sid = "AC74f43d5b916347f75d34f65622c90c90"
    twilio_account_token = "a69c27a3e02e4d008cae37877caa944d"
    twilio_phone_num = "+18339453253"
    your_phone_num = "+4068905678"
    client = Client(twilio_account_sid, twilio_account_token)

    client.messages.create(
        to=your_phone_num,
        from_=twilio_phone_num,
        body=message
    )

def main():
    duration = get_commmute_duration()
    duration_parts = duration.split()
    hours = int(duration_parts[0])
    minutes = int(duration_parts[2])
    duration_timedelta = datetime.timedelta(hours=hours, minutes=minutes)

    now = datetime.datetime.now()
    arrival_time = (now + duration_timedelta).strftime('%I:%M %p')

    message = (
        f"Hello!\n\n"
        f"Estimated commute at 9 am: {duration}\n\n"
        f"Leave now to arrive at approximately {arrival_time}.\n\n"
    )

    send_text_messages(message)

if __name__ == '__main__':
    main()






