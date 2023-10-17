# Works

# imports for the bot
import discord
from discord.ext import commands
import random
import os
import requests
import json

import logging
logging.basicConfig(level=logging.INFO)


# Code for intents and similar privilages. Keep
intent = discord.Intents.default()
intent.members = True
intent.message_content = True
bot = commands.Bot(command_prefix='!', intents=intent)


# example description
description = '''description'''

# creating bot object, description param, and intents param
bot = commands.Bot(command_prefix='!', description=description, intents=intent)

game_list = ['Fortnite', 'Overwatch2', 'Rainbow six siege']
items = ['$game', '$inspire', '$new', '$del', '$list', '$responding', '$hello', '$info']

bad_words = ['bad', 'horrendous','fuck']

warnings_dict = {}  # Dictionary to store user warnings


# event that prints loggin W
@bot.event
async def on_ready():
  print("We have logged in as {0.user}".format(bot))

# event that sends hello W
@bot.event
async def on_message(message):
  if message.author == bot.user:
    return
  await bot.process_commands(message)
  if message.author == bot.user:
    return
  msg = message.content
  if msg.startswith("$hello"):
    await message.channel.send("Hello and welcome!")
  if msg.startswith("$game"):
    await message.channel.send(random.choice(game_list))
  if msg.startswith("$items"):
    await message.channel.send(items)

  

# command that kicks when user mentioned W
@bot.command()
async def kick(ctx, member: discord.Member, *, reason=None):
  await member.kick(reason=reason)
  await ctx.send(f'User {member} has been kicked from the server.')

# command that kicks when user mentioned W
@bot.command()
async def ban(ctx, member: discord.Member, *, reason=None):
    await member.ban(reason=reason)
    await ctx.send(f'User {member} has been banned from the server.')

# command that deletes messages containgin bad words W
@bot.event
async def on_message(message):
    # Convert the message content to lowercase for comparison
    content_lower = message.content.lower()
    
    for i in bad_words:
        if i.lower() in content_lower:  # Convert each bad word to lowercase for comparison
            await message.delete()
            await message.channel.send(f"{message.author.mention}, your message was deleted for using a prohibited word.")
            break  # Break out of the loop once a bad word is found, no need to check further
    await bot.process_commands(message)  # So other commands can be processed

# command that gives warnings and bans after 3 warnings N
@bot.command()
async def warn(ctx, member: discord.Member):
    """Warns the mentioned user. If the user receives 3 warnings, they get banned."""
    
    if member.id in warnings_dict:
        warnings_dict[member.id] += 1
    else:
        warnings_dict[member.id] = 1

    count = warnings_dict[member.id]

    if count == 3:  # After 3 warnings
        await ctx.guild.ban(member, reason="Received 3 warnings.")
        await ctx.send(f"{member.mention} has been banned for receiving 3 warnings.")
        del warnings_dict[member.id]  # Reset the warning count for the member
    else:
        await ctx.send(f"{member.mention} has been warned. [{count}/3]")

@bot.command()
async def unban(ctx, *, user: str):
    # Split the user input into name and discriminator
    name, discriminator = user.split('#')

    # Find the banned user from the server's ban list
    banned_users = await ctx.guild.bans()
    
    if not banned_users:
        await ctx.send("There are no banned users in this server.")
        return

    for ban_entry in banned_users:
        banned_user = ban_entry.user
        if (banned_user.name, banned_user.discriminator) == (name, discriminator):
            await ctx.guild.unban(banned_user)
            await ctx.send(f"{banned_user.mention} has been unbanned!")
            return
    await ctx.send(f"User {user} was not found in the ban list.")


# secret and run statement
my_secret = ''
bot.run(my_secret)
