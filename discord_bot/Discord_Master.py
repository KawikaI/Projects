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

# example description
description = '''An example bot to showcase the discord.ext.commands extension
module.
There are a number of utility commands being showcased here.'''

# creating bot object, description param, and intents param
bot = commands.Bot(command_prefix='!', description=description, intents=intent)

game_list = ['Fortnite', 'Overwatch2', 'League of legends', 'Roblox', 'Titanfall', 'Outlast', 'Left for dead', 'GTA V', 'Pokemon', 'The legends of Zelda']
items = ['$game', '$inspire', '$new', '$del', '$list', '$responding', '$hello', '$info']


# event that prints loggin
@bot.event
async def on_ready():
  print("We have logged in as {0.user}".format(bot))

# event that sends hello WORKS
@bot.event
async def on_message(message):
  if message.author == bot.user:
    return
  msg = message.content
  
  if msg.startswith("$hello"):
    await message.channel.send("Hello and welcome!")
  
  if msg.startswith("$game"):
    await message.channel.send(random.choice(game_list))

  if msg.startswith("$items"):
    await message.channel.send(items)


# command that kicks when user mentioned
@bot.command()
async def kick(ctx, member: discord.Member, *, reason=None):
  await member.kick(reason=reason)
  await ctx.send(f'User {member} has been kicked from the server.')


# command that bans when user mentioned
@bot.command()
async def ban(ctx, member: discord.Member, *, reason=None):
  await member.ban(reason=reason)
  await ctx.send(f'User {member} has been banned from the server.')

  
# command that adds two numbers together !add 5 5
@bot.command()
async def add(ctx, left: int, right: int):
  await ctx.send(left + right)

# command that rolls dice
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


# command for choosing a random outcome !choose left right
@bot.command(description='For when you wanna settle the score some other way')
async def choose(ctx, *choices: str):
    """Chooses between multiple choices."""
    await ctx.send(random.choice(choices))

# command that repeats a message !repeat 2 k. Returns k twice
@bot.command()
async def repeat(ctx, times: int, content='repeating...'):
    """Repeats a message multiple times."""
    for i in range(times):
        await ctx.send(content)

# command that returns when a user joined !joined @user.returns date
@bot.command()
async def joined(ctx, member: discord.Member):
    """Says when a member joined."""
    await ctx.send(f'{member.name} joined {discord.utils.format_dt(member.joined_at)}')

  
# if user enters anything except for !cool bot returns statement
@bot.group()
async def cool(ctx):
    """Says if a user is cool.
    In reality this just checks if a subcommand is being invoked.
    """
    if ctx.invoked_subcommand is None:
        await ctx.send(f'No, {ctx.subcommand_passed} is not cool')

# if user inpute !cool bot   then returns 'Yes, the bot is cool.'
@cool.command(name='bot')
async def _bot(ctx):
    await ctx.send('Yes, the bot is cool.')


# secret and run statement
my_secret = ''
bot.run(my_secret)
