Hanoi Tower game
====

### Percepts ###
A list of available Percepts;

    disc(Name, Level)           Name     = Disc identifier.
                                Level    = Disc level / size. 
                                           Bigger discs can't be piled upon smaller ones.
                          
    pins(Amount)                Amount   = The amount of pins in this instance of the game.
    
    on(Name, Position, Next)    Name     = Disc identifier.
                                Position = Pin identifier.
                                Next     = Identifier of the disc below the current one.
                                           Is equal to 0 (zero) in case the disc is the last one.
                                           
### Actions ###
A list of available Actions;

    move(Disc, To)              Disc     = Disc identifier.
                                To       = Destination Pin.
                                           If a move is impossible, nothing happens.
