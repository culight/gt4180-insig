/*
 * Copyright 2011 Greg Milette and Adam Stroud
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package root.gast.playground.speech.visitor;

import org.apache.commons.codec.language.Soundex;

import root.gast.speech.text.match.SoundsLikeWordMatcher;

/**
 * mark soundex matches with a &
 * @author Greg Milette &#60;<a href="mailto:gregorym@gmail.com">gregorym@gmail.com</a>&#62;
 */
public class MatchesTargetVisitorSoundex extends MatchesTargetVisitor
{
    public MatchesTargetVisitorSoundex(String target)
    {
        super(target);
    }
    
    @Override
    protected String getMark()
    {
        return "&";
    }
    
    @Override
    protected String encode(String toEncode)
    {
        //encodes it
        SoundsLikeWordMatcher matcher = new SoundsLikeWordMatcher(toEncode);
        return matcher.getWords().iterator().next();
    }

}
