package org.example.Model;

import java.util.Date;

/**
 * Recordul Bill reprezinta o factura generata pentru o comanda.
 * Acesta este imutabil si contine informatii despre:
 * - ID-ul facturii
 * - ID-ul comenzii asociate
 * - data generarii
 * - pretul total
 *
 * Facturile sunt salvate in tabela Log si nu pot fi modificate dupa creare.
 *
 * @param id             ID-ul facturii.
 * @param orderId        ID-ul comenzii pentru care s-a emis factura.
 * @param generatedDate  Data la care a fost generata factura.
 * @param totalPrice     Pretul total facturat.
 */
public record Bill(int id, int orderId, Date generatedDate, float totalPrice) {
}