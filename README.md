android-pinview
===============

Android PinView to protect activities (fragments : later)

How to use?

from the eu.codlab.pin package, implements the Interface IPinEntryListener

onExit() > the user requested an exit, must be managed by the app

onPinEntered(int) > the listener must check if the result is ok AND return if true of false
to notify the manager it is ok - before returning, the listener MIUST manage it's UI to do whatever it grant the access

getListenerContext() must return a valid Context for the time of the operations

Finally, to register for events :

        final PinCheckHelper checker = new PinCheckHelper(IPinEntryListener);
        checker.register(Context);

What comes ?

Manage user pin code set (since for now, the user is not able to set is own pin code - must be managed by the program)
