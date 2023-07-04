# TNX-Utils
A utility Spigot plugin.

# Commands

| Name                         | Command                         | Description                                                       | Permission |
|------------------------------|---------------------------------|-------------------------------------------------------------------|------------|
| Vote                         | /vote [day\|night\|clear\|rain] | Vote for day, night, clear or rain                                | tnx.vote   |
| Rocket                       | /rocket [player]                | Launch a player in the air and let him explode                    | tnx.rocket |
| Creeper Explosion Protection | /cep [true\|false]              | Enable or disable Creeper explosion protection                    | tnx.cep    |
| Time/Weather Vote            | /twv [true\|false]              | Enable or disable time/weather vote                               | tnx.twv    |
| Boss Bar Broadcast           | /bb [seconds] [message]         | Create a Boss Bar broadcast message                               | tnx.bb     |
| Poll                         | /poll [seconds] [message]       | Create a poll and let people vote in chat with 1 (yes) and 2 (no) | tnx.poll   |
| Death Inventory              | /di [player]                    | Show the inventory of a player before he died                     | tnx.di     |

# Config

```yaml
CreeperExplosionProtection:
  enabled: true
  SlowRegen: true # Enable or disable the block regeneration animation
TimeWeatherVote:
  enabled: true
Death:
  Sound: true # Enable or disable sound on death
  Coordinates: true # Enable or disable coordinates of death in chat
  Firework: true # Enable or disable fireworks on death point
  Inventory: true # Enable or disable death inventory
Poll:
  excludePollStarter: true # Enable or disable exclusion of poll creator from poll
```