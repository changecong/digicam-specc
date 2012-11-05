Digicam SpecC
=============

use SpecC to implement a Jpeg Encoder

### Executation

> cd src/

> make test

### Introduction

> test.sh: used to execute the _digicam_ and test the result.
>> 1. generates N copies of image _ccd.bmp_.
>> 2. executes the _digicam_, put the output streams into _log.txt_
>> 3. (_Optional_) shows the outputs of each stage and compare them (by analyse the content in _log.txt_).
>> 4. compare the jpg images

> Makefile: used to compile and execute.
>> 1. make         --  compile and generate executable _digicam_.
>> 2. make test    --  compile and call _test.sh_

> comlog.sh: called by _test.sh_ to compare content in _log.txt_.
