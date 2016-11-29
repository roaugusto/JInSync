//    This file is part of JInSync, developed by Rodrigo Augusto Silva dos Santos
//
//    JInSync is free software: you can redistribute it and/or modify it under 
//    the terms of the GNU Less General Public License as published by the Free 
//    Software Foundation, either version 3 of the License, or (at your option) 
//    any later version.
//
//    JInSync is distributed in the hope that it will be useful, but WITHOUT ANY 
//    WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
//    FOR A PARTICULAR PURPOSE.  See the GNU Less General Public License for more 
//    details.
//
//    You should have received a copy of the GNU Less General Public License 
//    along with JInSync.  If not, see <http://www.gnu.org/licenses/>.    
//
//    -------------------------------------------------------------------------
//
//    Este arquivo é parte do programa JInSync, desenvolvido por Rodrigo Augusto
//    Silva dos Santos
//
//    JInSync é um software livre; você pode redistribuí-lo e/ou modificá-lo 
//    dentro dos termos da Licença Pública Geral Menor GNU como publicada 
//    pela Fundação do Software Livre (FSF); na versão 3 da Licença, ou 
//    (na sua opinião) qualquer versão.
//
//    Este programa é distribuído na esperança de que possa ser  útil, mas 
//    SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer 
//    MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral Menor 
//    GNU para maiores detalhes.
//
//    Você deve ter recebido uma cópia da Licença Pública Geral Menor GNU 
//    junto com este programa, Se não, veja <http://www.gnu.org/licenses/>.

package br.com.jinsync.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;


public final class PasswordSecret {
    private static SecretKey skey;
    private static KeySpec ks;
    private static PBEParameterSpec ps;
    private static final String algorithm = "PBEWithMD5AndDES";
    private static BASE64Encoder enc = new BASE64Encoder();
    private static BASE64Decoder dec = new BASE64Decoder();
    static {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
            ps = new PBEParameterSpec (new byte[]{3,1,4,1,5,9,2,6}, 20);
            ks = new PBEKeySpec ("EAkFeEen4m8YkO".toCharArray()); // esta ? a chave que voc? quer manter secreta.
            // Obviamente quando voc? for implantar na sua empresa, use alguma outra coisa - por exemplo,
            // "05Bc5hswRWpwp1sew+MSoHcj28rQ0MK8". Nao use caracteres especiais (como ?) para nao dar problemas.
            skey = skf.generateSecret (ks);
        } catch (java.security.NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (java.security.spec.InvalidKeySpecException ex) {
            ex.printStackTrace();
        }
    }
    public static final String encrypt(final String text)
        throws
        BadPaddingException,
        NoSuchPaddingException,
        IllegalBlockSizeException,
        InvalidKeyException,
        NoSuchAlgorithmException,
        InvalidAlgorithmParameterException {
        final Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, skey, ps);
        return enc.encode (cipher.doFinal(text.getBytes()));
    }
    public static final String decrypt(final String text)
        throws
        BadPaddingException,
        NoSuchPaddingException,
        IllegalBlockSizeException,
        InvalidKeyException,
        NoSuchAlgorithmException,
        InvalidAlgorithmParameterException {
        final Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, skey, ps);
        String ret = null;
        try {
            ret = new String(cipher.doFinal(dec.decodeBuffer (text)));
        } catch (Exception ex) {
        }
        return ret;
    }
}