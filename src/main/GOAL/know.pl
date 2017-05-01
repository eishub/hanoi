% on(Name, Pin, onDisk)
:- dynamic on/3.

%disc(Name, size).
:- dynamic disc/2.

% Pin is empty
empty(Pin) :- not(on(_, Pin, _)).
% Disc on pin is top disc (nothing on top of it)
topDisc(Disc, Pin, Size) :- on(Disc, Pin, _), disc(Disc, Size), not(on(_, Pin, Disc)).
