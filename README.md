Hanoi Tower game
====

### Percepts ###
A list of available Percepts;

    disc(Name, Level)           Name     = Disc identifier.
                                Level    = Disc level / size. 
                                           Bigger discs can't be piled upon smaller ones.
    
    on(Name, Position, Next)    Name     = Disc identifier.
                                Position = Pin identifier.
                                Next     = Identifier of the disc below the current one.
                                           Is equal to 0 (zero) in case the disc is the last one.
                                           
### Actions ###
A list of available Actions;

    move(Disc, To)              Disc     = Disc identifier.
                                To       = Destination Pin.
                                           If a move is impossible, nothing happens.

                                           
Release Procedure
=============

Ensure your ~/.m2/settings.xml file is as follows:

```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          http://maven.apache.org/xsd/settings-1.0.0.xsd">
	<servers>
		<server>
   			<id>github</id>
   			<username>YOUR_USERNAME</username>
   			<password>YOUR_PASSWORD</password>
		</server>
	</servers>
</settings>
```

Then call:

```
mvn deploy -DcreateChecksum=true
```

Note that you must have a public name and e-mail address set on GitHub for this to work correctly (https://github.com/settings/profile)