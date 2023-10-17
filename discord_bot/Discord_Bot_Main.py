# Works

# imports for the bot
import discord
from discord.ext import commands
import random
import os
import requests
import json


# Code for intents and similar privilages. Keep intents
intent = discord.Intents.default()
intent.members = True
intent.message_content = True
client = discord.Client(intents=intent)
description = '''description'''
bot = commands.Bot(command_prefix='!', description=description, intents=intent)





game_list = ['Fortnite', 'Overwatch2', 'League of legends', 'Roblox', 'Titanfall', 'Outlast', 'Left for dead', 'GTA V', 'Pokemon', 'The legends of Zelda']
items = ['$game', '$inspire', '$new', '$del', '$list', '$responding', '$hello', '$info']


# METHOD TO PRINT LOGIN
@client.event
async def on_ready():
  print("We have logged in as {0.user}".format(client))

# PRINTS A GREETING
@client.event
async def on_message(message):
  if message.author == client.user:
    return
  msg = message.content
  if msg.startswith("hello"):
    await message.channel.send("Hello and welcome!")
  if msg.startswith("game"):
    await message.channel.send(random.choice(game_list))
  if msg.startswith("items"):
    await message.channel.send(items)

  await bot.process_commands(message)


# METHOD TO KICK MEMBERS WIP
@bot.command()
async def kick(ctx, member: discord.Member, *, reason=None):
    if ctx.message.author.guild_permissions.kick_members: 
        await member.kick(reason=reason)
        await ctx.send(f'{member} has been kicked from the server.')
    else:
        await ctx.send("You don't have permission to use this command.")



# METHOD TO BAN USERS WIP
@bot.command()
async def ban(ctx, member: discord.Member, *, reason=None):
  await member.ban(reason=reason)
  await ctx.send(f'User {member} has been banned from the server.')



# METHOD TO ROLL DICE WIP
@bot.command()
async def roll(ctx, dice: str):
    """Rolls a dice in NdN format."""
    try:
        rolls, limit = map(int, dice.split('d'))
    except Exception:
        await ctx.send('Format has to be in NdN!')
        return
    result = ', '.join(str(random.randint(1, limit)) for r in range(rolls))
    await ctx.send(result)


@bot.command()
@commands.has_permissions(manage_messages=True)
async def clear(ctx, amount: int = 100):
    """Clears the last number of messages specified in the channel. Default is 100."""
    await ctx.channel.purge(limit=amount+1)  # +1 to also remove the command message itself


# METHOD FOR JOINING METHOD
@bot.command()
async def joined(ctx, member: discord.Member):
    """Says when a member joined."""
    await ctx.send(f'{member.name} joined {discord.utils.format_dt(member.joined_at)}')

  
# secret and run statement
my_secret = ''
client.run(my_secret)
