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

polls = {}

user_xp = {}
user_level = {}

def calculate_needed_xp(level):
    return 5 * (level**2) + 50 * level + 100

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

# command that gives warnings and bans after 3 warnings W
@bot.command()
async def warning(ctx, member: discord.Member):
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

# command that unbans users W
@bot.command()
async def unban(ctx, *, user: str):
    # Split the user input into name and discriminator
    name, discriminator = user.split('#')

    # Using an async for loop to iterate through banned users
    found = False
    async for ban_entry in ctx.guild.bans():
        banned_user = ban_entry.user
        if (banned_user.name, banned_user.discriminator) == (name, discriminator):
            await ctx.guild.unban(banned_user)
            await ctx.send(f"{banned_user.mention} has been unbanned!")
            found = True
            break

    if not found:
        await ctx.send(f"User {user} was not found in the ban list.")

# command that creates a poll W
@bot.command()
async def create_poll(ctx, question, *options):
    if len(options) < 2:
        await ctx.send("You need at least two options for a poll!")
        return

    if len(options) > 10:
        await ctx.send("You can have at most 10 options!")
        return

    description = []
    for i, option in enumerate(options, start=1):
        description.append(f"{i}. {option}")
    embed = discord.Embed(title=question, description="\n".join(description))
    message = await ctx.send(embed=embed)
    
    for i in range(1, len(options)+1):
        await message.add_reaction(str(i) + "\u20E3")

    polls[message.id] = options

# event for reactions W
@bot.event
async def on_reaction_add(reaction, user):
    if user.bot:
        return
    
    message_id = reaction.message.id
    if message_id not in polls:
        return

    emoji = reaction.emoji
    if emoji in [f"{i+1}\u20E3" for i in range(len(polls[message_id]))]:
        await reaction.message.remove_reaction(emoji, user)


# command for leveling system
@bot.event
async def on_message(message):
    if message.author == bot.user or message.author.bot:
        return
    
    user_id = message.author.id

    # Initialize the user if not present
    if user_id not in user_xp:
        user_xp[user_id] = 0
        user_level[user_id] = 1

    # Increase XP
    user_xp[user_id] += 1

    # Check for level up
    if user_xp[user_id] >= calculate_needed_xp(user_level[user_id]):
        user_level[user_id] += 1
        user_xp[user_id] = 0
        await message.channel.send(f"{message.author.mention} leveled up to {user_level[user_id]}!")

    await bot.process_commands(message)

# command for level W
@bot.command(name='level')
async def level(ctx, member: discord.Member = None):
    member = member or ctx.author
    lvl = user_level.get(member.id, 1)
    xp = user_xp.get(member.id, 0)
    needed_xp = calculate_needed_xp(lvl)
    await ctx.send(f'{member.display_name} is at level {lvl} with {xp}/{needed_xp} XP.')

# command for leaderboard W
@bot.command(name='leaderboard')
async def leaderboard(ctx):
    sorted_users = sorted(user_level.keys(), key=lambda x: (user_level[x], user_xp[x]), reverse=True)
    top_ten = sorted_users[:10]

    msg = "Top 10 users in the server:\n\n"
    for idx, user_id in enumerate(top_ten, 1):
        member = ctx.guild.get_member(user_id)
        lvl = user_level[user_id]
        xp = user_xp[user_id]
        msg += f"{idx}. {member.display_name} - Level {lvl}, XP {xp}/{calculate_needed_xp(lvl)}\n"
    
    await ctx.send(msg)

# command for next level W
@bot.command(name='nextlevel')
async def next_level(ctx, member: discord.Member = None):
    member = member or ctx.author
    lvl = user_level.get(member.id, 1)
    xp = user_xp.get(member.id, 0)
    needed_xp = calculate_needed_xp(lvl)
    await ctx.send(f'{member.display_name} needs {needed_xp - xp} more XP to reach level {lvl + 1}.')



# secret and run statement
my_secret = 'MTAzODU1MjAzMzg1NjMzNTk4NA.GYMcgJ.htwsY8bgQcMGb5BFn7h1b21FhXJO7-1ZDQPbmg'
bot.run(my_secret)


# Use !level to see their current level and XP.
# Use !level @Username to see the level and XP of another user.
# Use !leaderboard to see the top 10 users in the server by level and XP.
# Use !nextlevel to see how much more XP they need to reach the next level.
# Use !nextlevel @Username to see how much more XP another user needs to level up.
